# Usuario Administrador

El usuario administrador inicial es `system.admin:root`

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
	|   |____+ /sell                     #(StoreSellController)
	|   |    |__ /details
	|   |    |__ /images
	|   |
	|   |____+ /item/{id}                #(StoreItemController)
	|        |__ /details
	|        |__ /images
	|        |__ /comment
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
	    |____ /dashboard
	    |____ /categories
	    |    |__ /create
	    |    |__ /{categoryId}/edit
	    |____ /users
	    |    |__ /{userId}/roles
	    |
	    |____ /roles
	         |__ /create
