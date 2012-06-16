package goodwill


//import org.cyberneko.html.parsers.SAXParser


class MainController {
	def grailsApplication
	
//	def thing = {
//		def bar = grailsApplication.config.grails.serverURL
//	}
	
	String tmp
	
    def index() { tmp="kiyanosh"
		[myText : tmp] }
	
	def doSearch()
	{
		
		def thing = "${grailsApplication.config.grails.goodWillSearch}"
		thing = thing.replaceAll("CRITERIA", "tobacco")
		
//		render "dot search " + params.searchField
//		render " <p> the config dude ${thing}"
		
		def slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser())
		def searchUrl = new URL(thing)
		
		searchUrl.withReader { urlReader ->
			def html = slurper.parse(urlReader)
			def tr = html.'**'.find{it['@class'] == 'productresults'}.tbody.tr.list()
			
			[myIDList : tr]
			/*
			tr.each {
				def myId = it.th[0].text()
				def myImg = it.th[1].img
//				render "found id ${myId} img ${myImg} <p>"
//				render "found name ${it.text()} <p>"
			}
			*/
		}
		
//		def seeds = ["http://labs.keplarllp.com/shop"]
//		
//	   // Load the NekoHTML parser with Xerces - this lets us parse the HTML.
//	   def slurper = null //new XmlSlurper(new SAXParser())
//	   
//	   // Now let's loop through each seed URL in turn.
//	   seeds.each() {
//		
//		   render "Accessing seed URL ${it}"
//		   def seedURL = new URL(it)
//
//
//		   seedURL.withReader { seedReader ->
//			   def seedHTML = slurper.parse(seedReader)
//			   
//			   render "<br><p> hello" + seedURL.toString()
//			   return
//	
//			   // Show the title of the seed page we're parsing.
//			   render "Seed page title is ${seedHTML.depthFirst().grep{ it.name() == 'TITLE'}}"
//			   return
//			   
//			   // Now loop through and find all the product links on this page.
//			   // For our purposes, a product link is any A tag inside a box div on the page.
//			   def productLinks = seedHTML.depthFirst().grep{ it.name() == 'DIV' && it.@class == 'box' }.collect { it.A.'@href'.toString() }
//			   productLinks.each {
//		
//				   println "  Accessing product URL ${it}"
//				   def productURL = new URI(seedURL.toString()).resolve(it).toURL()
//		
//				   productURL.withReader { productReader ->
//					   def productHTML = slurper.parse(productReader)
//		
//					   // Now display the product name.
//					   println "    Name is ${productHTML.depthFirst().grep{ it.name() == 'H1'}}"
//		
//					   // Now display the product description.
//					   println "    Description is ${productHTML.depthFirst().grep{ it.name() == 'P' && it.@class == 'ProductDescription'}}"
//		
//					   // Now need to grab the product price.
//					   println "    Price is ${productHTML.depthFirst().grep{ it.name() == 'P' && it.@class == 'ProductPrice'}}"
//				   }
//			   }
//		   }
//	   }
	}
}
