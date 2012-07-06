package goodwill

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.URIBuilder
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.HTML
import static groovyx.net.http.ContentType.TEXT
import org.apache.http.HttpRequest
import org.apache.http.HttpResponse
import org.apache.http.protocol.HttpContext
import org.apache.http.impl.client.DefaultRedirectHandler
import org.apache.http.impl.cookie.BasicClientCookie
import org.springframework.dao.DataIntegrityViolationException


class SearchController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def showResults()
	{
		def thing = "${grailsApplication.config.grails.goodWillSearch}"
		thing = thing.replaceAll("CRITERIA", params.searchField.replaceAll(" ", "+"))
		
		def slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser())
		def searchUrl = new URL(thing)
		def foundStuff = false
		
		searchUrl.withReader { urlReader ->
			def html = slurper.parse(urlReader)
			def tr = null
			if (html!=null && html.'**'.find{it['@class'] == 'productresults'}!=null)
            {
				tr = html.'**'.find{it['@class'] == 'productresults'}.tbody.tr.list()
				foundStuff = true
            }
				
			
			[myIDList : tr, recordsFound : foundStuff]
		}
	}
    
    def showMySearches()
    {
        def respString
//        def http = new HTTPBuilder("https://www.shopgoodwill.com/buyers/dologin.asp")
        def http = new HTTPBuilder("https://www.shopgoodwill.com/buyers/")
        def postbody =  [buyerid: "test", buyerpasswd: "test", submit: "Sign In", state: "1", cookcheck: "1", redir: ""]
        
//        def cookie = new BasicClientCookie("ASPSESSIONIDQQAQACAS", "KANMPJODBHBLFPNDELHOCJFJ")
//        http.client.cookieStore.addCookie(cookie)
//        cookie = new BasicClientCookie("CookieTest", "ishere")
//        http.client.cookieStore.addCookie(cookie)
        
//        def http = new HTTPBuilder("http://www.w3schools.com/TAGS/")
//        def postbody =  [fname: "kamdar", lname: "pass"]
        
//        http.auth.basic "test", "mypass"

        // didn't seem to work        
//        http.client.setRedirectHandler(new DefaultRedirectHandler() {
//              @Override
//              boolean isRedirectedRequested(HttpResponse response, HttpContext context) {
//                def redirected = super.isRedirectedRequested(response, context)
//                return redirected || response.getStatusLine().getStatusCode() == 302
//              }
//        })

        
//        http.handler.'302' = { resp ->
//            respString = " mememe "
//            def headers = resp.headers
//            respString += headers.'Location'
//            def myUrl = new URIBuilder(headers.'Location')
//            def queries = myUrl.query
//            respString += " stuff is " + queries.'vMsgNum'
//            }
        
        http.get( contentType: TEXT)
        {resp ->
                respString = "from get status is ${resp.statusLine}" 

                resp.headers.each {
                    respString +=it
                    respString += "<p><br>"
                }
        }
        
        http.post( path: "dologin.asp", body: postbody, requestContentType: URLENC)
            { resp ->
                respString = "got the response from goodwill ${resp.statusLine}"
                
                resp.headers.each {
                    respString +=it
                    respString += "<p><br>"
                }
            }
            
            http.client.cookieStore.cookies.each {
                respString += "$it.name : $it.value  | ${it.domain}${it.path}  |  $it.expiryDate" + "<br>"
            }
            
//            http.post( path: "demo_form_method_post.asp", body: postbody, requestContentType: URLENC)
//            { resp ->
//                respString = "got the response from goodwill ${resp.statusLine}<p><br>"
//                
//                resp.headers.each {
//                    respString += it
//                }
//            }

        [mysearches : respString]
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [searchInstanceList: Search.list(params), searchInstanceTotal: Search.count()]
    }

    def create() {
        [searchInstance: new Search(params)]
    }

    def save() {
        def searchInstance = new Search(params)
        if (!searchInstance.save(flush: true)) {
            render(view: "create", model: [searchInstance: searchInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'search.label', default: 'Search'), searchInstance.id])
        redirect(action: "show", id: searchInstance.id)
    }

    def show() {
        def searchInstance = Search.get(params.id)
        if (!searchInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'search.label', default: 'Search'), params.id])
            redirect(action: "list")
            return
        }

        [searchInstance: searchInstance]
    }

    def edit() {
        def searchInstance = Search.get(params.id)
        if (!searchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'search.label', default: 'Search'), params.id])
            redirect(action: "list")
            return
        }

        [searchInstance: searchInstance]
    }

    def update() {
        def searchInstance = Search.get(params.id)
        if (!searchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'search.label', default: 'Search'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (searchInstance.version > version) {
                searchInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'search.label', default: 'Search')] as Object[],
                          "Another user has updated this Search while you were editing")
                render(view: "edit", model: [searchInstance: searchInstance])
                return
            }
        }

        searchInstance.properties = params

        if (!searchInstance.save(flush: true)) {
            render(view: "edit", model: [searchInstance: searchInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'search.label', default: 'Search'), searchInstance.id])
        redirect(action: "show", id: searchInstance.id)
    }

    def delete() {
        def searchInstance = Search.get(params.id)
        if (!searchInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'search.label', default: 'Search'), params.id])
            redirect(action: "list")
            return
        }

        try {
            searchInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'search.label', default: 'Search'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'search.label', default: 'Search'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
