package goodwill

class User {
	String userName
	String userEmail
	Date lastLoggedin
	
	static hasMany = [searches : Search]

    static constraints = { userName unique:true  
    }
}
