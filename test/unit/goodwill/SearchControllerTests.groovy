package goodwill



import org.junit.*
import grails.test.mixin.*

@TestFor(SearchController)
@Mock(Search)
class SearchControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/search/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.searchInstanceList.size() == 0
        assert model.searchInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.searchInstance != null
    }

    void testSave() {
        controller.save()

        assert model.searchInstance != null
        assert view == '/search/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/search/show/1'
        assert controller.flash.message != null
        assert Search.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/search/list'


        populateValidParams(params)
        def search = new Search(params)

        assert search.save() != null

        params.id = search.id

        def model = controller.show()

        assert model.searchInstance == search
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/search/list'


        populateValidParams(params)
        def search = new Search(params)

        assert search.save() != null

        params.id = search.id

        def model = controller.edit()

        assert model.searchInstance == search
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/search/list'

        response.reset()


        populateValidParams(params)
        def search = new Search(params)

        assert search.save() != null

        // test invalid parameters in update
        params.id = search.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/search/edit"
        assert model.searchInstance != null

        search.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/search/show/$search.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        search.clearErrors()

        populateValidParams(params)
        params.id = search.id
        params.version = -1
        controller.update()

        assert view == "/search/edit"
        assert model.searchInstance != null
        assert model.searchInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/search/list'

        response.reset()

        populateValidParams(params)
        def search = new Search(params)

        assert search.save() != null
        assert Search.count() == 1

        params.id = search.id

        controller.delete()

        assert Search.count() == 0
        assert Search.get(search.id) == null
        assert response.redirectedUrl == '/search/list'
    }
}
