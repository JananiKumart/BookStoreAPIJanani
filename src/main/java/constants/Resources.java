package constants;
//enum is special class in java which has collection of constants or  methods
public enum Resources {
	Register("/register"),
	AlreadyRegister("/register/already"),
	Login("/login"),
	Books("/books/"),
	EditBookId("/books/1"),
	EditBook("/editedBooks/1"),
	GetBookId("/books/1"),
	DeleteBookId("/books/1"),
	DeleteBookIdNotFound("/books/NotFound"),
	GetAllBooks("/AllBooks"),
	InvalidBookRecord("/InvalidBooks");

	private String resource;
	Resources(String resource)
	{
		this.resource=resource;
	}
	public String getResource()
	{
		return resource;
	}
	
}
