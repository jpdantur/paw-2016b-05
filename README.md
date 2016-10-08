# Routes

	+ /
	|___+ /auth                          #(AuthenticationController)
	|   |____ /login
	|   |____ /register
	|
	|___+ /store
	|   |____+ /items                    #(StoreSearchController)
	|   |    |__ /all
	|   |    |__ /{id}
	|   |
	|   |____+ /sell                     #(StoreController)
	|   |    |__ /details
	|   |    |__ /images
	|   |
	|   |____+ /item/{id}                #(StoreItemController)
	|        |__ /details
	|        |__ /images
	|
	|___+ /profile                       #(ProfileController)
	|   |____ /details
	|   |____ /published
	|
	|___+ /images                        #(ImageController)
	|   |___ /get/{imageId}
	|   |___ /upload/{itemId}
	|
	|___+ /admin                         #(AdminController)
	    |____/categories