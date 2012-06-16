package goodwill

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
        [mysearches : "nothing"]
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
