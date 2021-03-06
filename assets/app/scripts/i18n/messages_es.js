'use strict';
define([], function () {

	return {
    '403.headline': 'Acceso Denegado',
    '403.home': 'Ir al Inicio',
    '404.headline': 'Recurso no encontrado',
    '404.home': 'Ir al inicio',
    '500.headline': 'Uy, puede ser que nos hayamos equivocado',
    '500.home': 'Ir al inicio',
    'Digits.SellForm.price': 'El articulo no puede ser gratis ni costar mas que mil millones',
    'Email.ProfileForm.email': 'El formato del email es invalido',
    'Email.RegisterForm.email': 'El formato del email es invalido',
    'Email.SellForm.email': 'El formato del email es invalido',
    'EmailExists.ProfileForm.email': 'El email ya existe',
    'EmailExists.RegisterForm.email': 'El email ya existe',
    'EmailNotExists.PasswordRecoveryForm.email': 'El email ingresado no existe en nuestros registros',
    'Max.SellForm.price': 'El precio no debe superar los ${1}',
    'Min.SellForm.price': 'El precio debe ser al menos ${1}',
    'NotEmpty.CommentForm.content': 'La resena no puede estar vacia',
    'NotEmpty.ProfileForm.email': 'El email no puede estar vacio',
    'NotEmpty.RegisterForm.email': 'El email no puede estar vacio',
    'NotEmpty.SellForm.email': 'Email es requerido',
    'NotNull.SellForm.price': 'El precio es obligatorio',
    'PasswordMatch.ChangePasswordForm.repeatPassword': 'Las contraseñas no coinciden',
    'PasswordMatch.RegisterForm.repeatPassword': 'Las contraseñas no coinciden',
    'Pattern.RoleForm.slug': 'El nombre del slug debe contener caracteres en mayuscula, numeros, \'-\' o \'_\' sin espacios',
    'Size.CategoryForm.name': 'El nombre debe contener entre {min} y {max} caracteres',
    'Size.CommentForm.content': 'La resena debe contener hasta {max} caracteres',
    'Size.CommentForm.contentFixed': 'La resena debe contener hasta 300 caracteres',
    'Size.RegisterForm.firstName': 'Su nombre debe contener entre {min} y {max} caracteres',
    'Size.RegisterForm.lastName': 'Su apellido debe contener entre {min} y {max} caracteres',
    'Size.RegisterForm.password': 'Su contraseña debe contener entre {min} y {max} caracteres',
    'Size.RegisterForm.username': 'Su usuario debe contener entre {min} y {max} caracteres',
    'Size.RoleForm.name': 'El nombre del rol debe contener entre {min} y {max} caracteres',
    'Size.RoleForm.slug': 'El nombre del slug debe contener entre {min} y {max} caracteres',
    'Size.SellForm.name': 'El nombre del articulo debe contener entre {min} y {max} caracteres',
    'UsernameExists.RegisterForm.username': 'El usuario ya existe',
    'WrongPassword.ChangePasswordForm.oldPassword': 'Esta no era su contraseña',
    'admin.categories.create.action': 'Crear',
    'admin.categories.create.cancel': 'Cancelar',
    'admin.categories.create.headline': 'Crear Categoria',
    'admin.categories.create.name': 'Nombre',
    'admin.categories.create.success': 'Creacion Exitosa',
    'admin.categories.create.successInfo': 'Esta ventana se cerrara automaticamente',
    'admin.categories.rename.action': 'Renombrar',
    'admin.categories.rename.headline': 'Renombrando categoria',
    'admin.categories.rename.name': 'Nombre',
    'admin.categories.rename.success': 'Se actualizo correctamente',
    'admin.categories.rename.successInfo': 'Esta ventana se cerrara automaticamente',
    'admin.category.add': 'Nueva Subcategoria',
    'admin.category.headline': 'Categorias',
    'admin.category.rename': 'Renombrar',
    'admin.index.categories': 'Categorias',
    'admin.index.headline': 'Panel de Control',
    'admin.index.items': 'Articulos',
    'admin.index.users': 'Usuarios',
    'admin.roles.add': 'Agregar',
    'admin.roles.create.headline': 'Crear Rol',
    'admin.roles.create.name': 'Nombre',
    'admin.roles.default': 'Por Defecto',
    'admin.roles.defaultFail': 'El rol no pude hacerse el por defecto',
    'admin.roles.defaultSuccess': 'Se ha cambiado el rol por defecto',
    'admin.roles.headline': 'Roles',
    'admin.roles.isDefault': 'Rol por defecto',
    'admin.roles.makeDefault': 'Hacer por defecto',
    'admin.roles.name': 'Nombre',
    'admin.sidebar.roles': 'Roles',
    'admin.users.add': 'Agregar',
    'admin.users.create.email': 'Email',
    'admin.users.create.headline': 'Crear Usuario',
    'admin.users.create.lastName': 'Apellido',
    'admin.users.create.mainRole': 'Rol Principal',
    'admin.users.create.name': 'Nombre',
    'admin.users.create.password': 'Contraseña',
    'admin.users.create.repeatPassword': 'Repetir Contraseña',
    'admin.users.create.username': 'Usuario',
    'admin.users.edit.add': 'Agregar',
    'admin.users.edit.addRole': 'Nuevo Rol',
    'admin.users.edit.headline': 'Roles',
    'admin.users.editRoles': 'Editar Roles',
    'admin.users.email': 'Email',
    'admin.users.headline': 'Usuarios',
    'admin.users.lastName': 'Apellido',
    'admin.users.name': 'Nombre',
    'admin.users.roles': 'Roles',
    'admin.users.username': 'Usuario',
    'auth.forgotpass.success.message': 'Revise su casilla de correo',
    'auth.forgotpass.success.title': 'El email ha sido enviado',
    'edit.action': 'Editar',
    'edit.details': 'Detalles',
    'edit.details.categoryChosen': 'Usetd eligio:',
    'edit.headline': 'Editando',
    'edit.images': 'Imagenes',
    'index.categories': 'Categorias',
    'index.favourite.empty': 'No tiene favoritos, agregue favoritos para verlos',
    'index.favourite.remove': 'Remover',
    'index.favourite.title': 'Favoritos',
    'index.favourite.viewAll': 'Ver Todos',
    'index.headline': '¿Que desea Comprar?',
    'index.login': 'Ingresar',
    'index.promoted': 'Mas Vendidos',
    'index.register': 'Registrate',
    'index.search': 'Buscar',
    'index.sell': 'Vender',
    'index.user.items': 'Mis Articulos',
    'index.user.logout': 'Cerrar Sesion',
    'index.user.profile': 'Mi Perfil',
    'index.user.purchases': 'Mis Compras',
    'index.user.sales': 'Mis Ventas',
    'item.bought.underRevision': 'Su compra se encuentra bajo revision del vendedor.',
    'item.bought.viewStatus': 'Vea el estado aquí',
    'item.buy': 'Comprar',
    'item.comment': 'Reseña',
    'item.commentAction': 'Enviar',
    'item.commentEmpty': 'No hay reseñas aún',
    'item.comments': 'Reseñas',
    'item.commentRequired': 'Debe insertar una reseña',
    'item.commentLong': 'La reseña es muy larga',
    'item.commentShort': 'La reseña es muy corta',
    'item.description': 'Descripción',
    'item.favourite': 'Marcar como favorito',
    'item.item': 'Estado del Articulo',
    'item.new': 'Nuevo',
    'item.carousel.previous': 'Anterior',
    'item.carousel.next': 'Siguiente',
    'item.notifications.draft.message': 'Este articulo es un borrador. Haga click aquí para editarlo',
    'item.notifications.draft.title': 'Esto es un borrador',
    'item.notifications.paused.alternative': 'O haga click aquí para editarla',
    'item.notifications.paused.message': 'Esta publicacion esta pausada. Haga click aquí para resumirla',
    'item.notifications.paused.title': 'Esta publicacion esta pausada',
    'item.seller': 'Vendedor',
    'item.seller.publications': 'Publicaciones del mismo vendedor',
    'item.sold': 'vendidos',
    'item.used': 'Usado',
    'item.score': 'Puntaje',
    'login.error.error': '¡Error!',
    'login.error.wrongData': 'Usuario o contraseña incorrectos',
    'login.field.password': 'Contraseña',
    'login.field.rememberMe': 'Recordarme',
    'login.field.user': 'Usuario',
    'login.form.noUserQuestion': '¿No tiene un usuario aún?',
    'login.form.register': 'Registrese',
    'login.recover.help': 'Ingrese su correo y le enviaremos las instrucciones para recuperar su contraseña',
    'login.recover.invalidLink': 'El link es invalido',
    'login.recover.maxTime': 'El tiempo máximo para renovar su contraseña es de 3 horas',
    'login.recover.question': '¿Olvidó su contraseña?',
    'login.recover.recover': 'Recuperar',
    'login.recover.recoverIt': 'Recuperela',
    'login.recover.emailRequired': 'Debe ingresar un email',
    'login.recover.emailInvalid': 'Ingrese un email válido',
    'login.submit.login': 'Iniciar Sesion',
    'profile.account.form.email': 'Email',
    'profile.loading': 'Cargando...',
    'profile.account.form.lastName': 'Apellido',
    'profile.account.form.name': 'Nombre',
    'profile.account.form.update': 'Modificar',
    'profile.account.form.username': 'Usuario',
    'profile.account.title': 'Cuenta',
    'profile.close': 'Cerrar',
    'profile.favourites.table.item': 'Articulo',
    'profile.favourites.table.search': 'Buscar',
    'profile.favourites.table.seller': 'Vendedor',
    'profile.favourites.title': 'Favoritos',
    'profile.items.active': 'Publicaciones Activas',
    'profile.items.all': 'Todas las Publicaciones',
    'profile.items.drafts': 'Borradores',
    'profile.items.paused': 'Publicaciones Pausadas',
    'profile.items.table.edit': 'Editar',
    'profile.items.table.edit_images': 'Editar Imagenes',
    'profile.items.table.item': 'Articulo',
    'profile.items.table.name': 'Nombre',
    'profile.items.table.pause': 'Pausar Publicacion',
    'profile.items.table.picture': 'Foto',
    'profile.items.table.price': 'Precio',
    'profile.items.table.publish': 'Publicar',
    'profile.items.table.resume': 'Reanudar Publicacion',
    'profile.items.table.search': 'Buscar',
    'profile.items.table.sold': 'Vendidos',
    'profile.items.table.status': 'Estado',
    'profile.items.title': 'Articulos Publicados',
    'profile.modal.scores.buyer': 'Comprador',
    'profile.modal.scores.close': 'Cerrar',
    'profile.modal.scores.error': 'Error',
    'profile.modal.scores.lowerBuyer': 'comprador',
    'profile.modal.scores.lowerSeller': 'vendedor',
    'profile.modal.scores.score': 'Puntaje',
    'profile.modal.scores.scoreBuyer': '¿Desea calificar al comprador ahora?',
    'profile.modal.scores.scoresFrom': 'Puntuacion del',
    'profile.modal.scores.scoringTo': 'Puntuando al',
    'profile.modal.scores.seller': 'Vendedor',
    'profile.modal.scores.stillNoScore': 'Esta venta no ha sido puntuada aún',
    'profile.modal.scores.success': 'Puntuacion registrada exitósamente',
    'profile.modal.scores.title': 'Viendo Puntajes',
    'profile.modal.scores.toScore': 'Puntuar',
    'profile.modal.scores.voidContent': 'Contenido vacio',
    'profile.modal.scores.yourScore': 'Su puntuacion',
    'profile.password.form.current': 'Contraseña Actual',
    'profile.password.form.new': 'Nueva Contraseña',
    'profile.password.form.repeat': 'Repetir Nueva Contraseña',
    'profile.password.title': 'Contraseña',
    'profile.public.buyer': 'comprador',
    'profile.public.nopurchases': 'no ha comprado ningun articulo aún',
    'profile.public.nosales': 'no ha vendido ningun articulo aún',
    'profile.public.score.buyer': 'Puntaje como comprador',
    'profile.public.score.buyer.acceptance': 'Porcentaje de Aceptacion',
    'profile.public.score.buyer.acceptedPurchases': 'Compras Aprobadas',
    'profile.public.score.buyer.rejectedPurchases': 'Compras Rechazadas',
    'profile.public.score.seller': 'Puntaje como vendedor',
    'profile.public.score.seller.approvedSales': 'Ventas Aprobadas',
    'profile.public.score.seller.recommendation': 'Porcentaje de Recomendación',
    'profile.public.score.seller.rejectedSales': 'Ventas Rechazadas',
    'profile.public.seller': 'vendedor',
    'profile.public.seller.items': 'Articulos Publicados',
    'profile.public.viewProfileOf': 'Ver perfil como',
    'profile.purchases.approved': 'Compras Aprobadas',
    'profile.purchases.declined': 'Compras Denegadas',
    'profile.purchases.pending': 'Compras Abiertas',
    'profile.purchases.seeScores': 'Ver puntajes',
    'profile.purchases.seller': 'Vendedor',
    'profile.purchases.table.date': 'Fecha',
    'profile.purchases.table.item': 'Articulo',
    'profile.purchases.table.score': 'Puntaje',
    'profile.purchases.table.search': 'Buscar',
    'profile.purchases.table.seller': 'Vendedor',
    'profile.purchases.title': 'Compras',
    'profile.sales.approved': 'Ventas Aprobadas',
    'profile.sales.danger': 'RECHAZAR',
    'profile.sales.declined': 'Ventas Denegadas',
    'profile.sales.nothing': 'No Hay Nada',
    'profile.sales.pending': 'Ventas Abiertas',
    'profile.sales.seeScores': 'Ver puntajes',
    'profile.sales.sell.approve': 'aprobar',
    'profile.sales.sell.approved': 'Venta Aprobada',
    'profile.sales.sell.approving': 'aprobando su venta',
    'profile.sales.sell.confirmation': '¿Esta seguro que desea',
    'profile.sales.sell.confirmation2': 'esta venta?',

    'profile.sales.sell.yesSell': '¿Esta seguro que desea aprobar esta venta?',
    'profile.sales.sell.noSell': '¿Esta seguro que desea denegar esta venta?',

    'profile.sales.sell.error': 'Hubo un error',
    'profile.sales.sell.pause': 'pausar',
    'profile.sales.sell.pauseBtn': 'Pausar Publicacion',
    'profile.sales.sell.pausing': 'pausando su publicacion',
    'profile.sales.sell.publishConfirmation': '¿Esta seguro que desea publicar este articulo?',
    'profile.sales.sell.published': 'Publicado',
    'profile.sales.sell.publishing': 'publicando su articulo',
    'profile.sales.sell.reject': 'rechazar',
    'profile.sales.sell.rejected': 'Venta Rechazada',
    'profile.sales.sell.rejecting': 'rechazando su venta',
    'profile.sales.sell.resume': 'reanudar',
    'profile.sales.sell.resumeBtn': 'Reanudar Publicacion',
    'profile.sales.sell.resuming': 'resumiendo su publicacion',
    'profile.sales.sell.success': 'Accion realizada con éxito',
    'profile.sales.success': 'APROBAR',
    'profile.sales.table.buyer': 'Comprador',
    'profile.sales.table.date': 'Fecha',
    'profile.sales.table.item': 'Articulo',
    'profile.sales.table.score': 'Puntajes',
    'profile.sales.table.search': 'Buscar',
    'profile.sales.title': 'Ventas',
    'register.form.createAccount': 'Crear Cuenta',
    'register.form.hasAccount': '¿Ya tiene una cuenta?',
    'register.form.login': 'Ingrese aquí',
    'register.form.mail': 'E-mail',
    'register.form.name': 'Nombre',
    'register.form.repeatPassword': 'Repetir contraseña',
    'register.form.surname': 'Apellido',
    'register.form.nameRequired': 'Debe Ingresar un Nombre',
    'register.form.nameShort': 'El Nombre es muy Corto',
    'register.form.nameLong': 'El Nombre es muy Largo',
    'register.form.namePattern': 'El Nombre solo puede contener letras',
    'register.form.surnameRequired': 'Debe Ingresar un Apellido',
    'register.form.surnameShort': 'El Apellido es muy corto',
    'register.form.surnameLong': 'El Apellido es muy largo',
    'register.form.surnamePattern': 'El Apellido solo puede contener letras',
    'register.form.emailRequired': 'Debe Ingresar un Email',
    'register.form.emailInvalid': 'Ingrese un email válido',
    'register.form.usernameRequired': 'Debe Ingresar un Usuario',
    'register.form.usernameShort': 'El Usuario es muy corto',
    'register.form.usernameLong': 'El Usuario es muy largo',
    'register.form.usernamePattern': 'El Usuario solo puede contener letras, números, - o _',
    'register.form.passwordShort': 'La contraseña es muy corta',
    'register.form.passwordLong': 'La contraseña es muy larga',
    'register.form.passwordMatch': 'Las contraseñas deben coincidir',
    'register.form.passwordRequired': 'Debe Ingresar una Contraseña',
    'search.filters.applied': 'Filtros Aplicados',
    'search.filters.categories': 'Categorias',
    'search.filters.categories.apply': 'Aplicar',
    'search.filters.order.alphabetic': 'Alfabetico',
    'search.filters.order.by': 'Ordenar por',
    'search.filters.order.priceAsc': 'Menor Precio',
    'search.filters.order.priceDesc': 'Mayor Precio',
    'search.filters.order.recentAsc': 'Menos Reciente',
    'search.filters.order.recentDesc': 'Más Reciente',
    'search.filters.order.soldAsc': 'Menos Vendidos',
    'search.filters.order.soldDesc': 'Más Vendidos',
    'search.filters.pagination.modifiers.amount': 'Artículos',
    'search.filters.pagination.modifiers.of': 'de',
    'search.filters.pagination.modifiers.perPage': 'resultados por página',
    'search.filters.pagination.modifiers.showing': 'Mostrando',
    'search.filters.pagination.pagers.first': 'Primera',
    'search.filters.pagination.pagers.last': 'Última',
    'search.filters.pagination.pagers.next': 'Próxima',
    'search.filters.pagination.pagers.previous': 'Anterior',
    'search.filters.price': 'Precio',
    'search.filters.price.between': 'Entre {{minPrice}} y {{maxPrice}}',
    'search.filters.price.from': 'A partir de {{minPrice}}',
    'search.filters.price.max': 'Max',
    'search.filters.price.min': 'Min',
    'search.filters.price.to': 'Hasta {{maxPrice}}',
    'search.none': 'No hay resultados para su búsqueda',
    'search.searchResults': 'Resultados de la búsqueda',
    'sell.back': 'Volver',
    'sell.skip': 'Saltear',
    'sell.edit.details.success': '¡Enhorabuena! Su artículo se actualizó exitósamente.',
    'sell.edit.return': 'Volver a Mis Artículos',
    'sell.field.category': 'Categoría',
    'sell.field.contactEmail': 'Email de Contacto',
    'sell.field.description': 'Descripción',
    'sell.field.name': 'Nombre',
    'sell.field.nameRequired': 'Debe ingresar un Nombre',
    'sell.field.nameShort': 'El nombre es muy corto',
    'sell.field.nameLong': 'El nombre es muy largo',
    'sell.field.pictures': 'Fotos',
    'sell.field.price': 'Precio',
    'sell.field.priceRequired': 'Debe ingresar un precio',
    'sell.field.priceNegative': 'El precio no puede ser negativo',
    'sell.field.priceStep': 'El precio no debe tener más de 2 decimales',
    'sell.field.publish': 'Publicar Ahora',
    'sell.field.radio.no': 'No',
    'sell.field.radio.yes': 'Si',
    'sell.field.status.new': 'Nuevo',
    'sell.field.status.title': 'Estado',
    'sell.field.status.used': 'Usado',
    'sell.field.used': 'Usado',
    'sell.headline': 'Vender un producto',
    'sell.image.upload.drag': 'Arrastre sus imágenes o haga click para seleccionarlas',
    'sell.image.upload.headline': 'Subir fotos para',
    'sell.ok': 'Aceptar y Subir Imágenes',
    'sell.okImages': 'Subir Imágenes',
    'sell.pickCategory': 'Por favor elija una categoría',
    'successMessages.buyItem.error': 'La compra no se realizo con éxito',
    'successMessages.buyItem.success': '¡Enhorabuena! La compra se ha realizado con éxito',
    'successMessages.changePassword.content': 'Se han modificado sus datos',
    'successMessages.changePassword.contentFail': 'No se han modificado sus datos',
    'successMessages.changePassword.title': 'Enhorabuena',
    'successMessages.changePassword.titleFail': 'Lo sentimos',
    'successMessages.publishItem.action': 'Publicar otro',
    'successMessages.publishItem.content': 'Su producto ha sido publicado con éxito',
    'successMessages.publishItem.content2': 'Su producto ha sido guardado con éxito',
    'successMessages.publishItem.title': 'Enhorabuena',
    'successMessages.toggleFavourite.addError': 'No se ha podido agregar a favoritos',
    'successMessages.toggleFavourite.addSuccess': 'Se ha agregado a favoritos',
    'successMessages.toggleFavourite.removeError': 'No se ha podido agregar a favoritos',
    'successMessages.toggleFavourite.removeSuccess': 'Se ha agregado a favoritos',

    'ng.messages.logoutSuccessful': 'Cierre de Sesión éxitoso',
    'ng.messages.loginSuccessful': 'Inicio de sesión éxitoso',
    'ng.messages.loginError': 'Usuario o Contraseña incorrecto',
    'ng.messages.editSuccess': 'Articulo actualizado exitósamente',
    'ng.messages.editError': 'Ocurrió un error actualizando el articulo',
    'ng.messages.imageSuccess': 'Las imagenes se han subido exitósamente',
    'ng.messages.imageError': 'Ha ocurrido un error subiendo las imagenes',
    'ng.messages.reviewSuccess': 'Su reseña ha sido guardada exitósamente',
    'ng.messages.reviewError': 'Ocurrió un error guardando su reseña',

    'ng.messages.emailSuccess': 'Email actualizado exitósamente',
    'ng.messages.emailError': 'Ocurrió un error actualizando su email',
    'ng.messages.passwordSuccess': 'Contraseña actualizada exitósamente',
    'ng.messages.passwordError': 'Ocurrió un error actualizando su contraseña',
    'ng.messages.itemSuccess': 'Publicación actualizada exitósamente',
    'ng.messages.itemError': 'Ocurrió un error actualizando su publicación',
    'ng.messages.purchaseSuccess': 'Compra aprobada exitósamente',
    'ng.messages.purchaseError': 'Ocurrió un error aprobando su compra',
    'ng.messages.purchaseSuccess2': 'Compra rechazada exitósamente',
    'ng.messages.purchaseError2': 'Ocurrió un error rechazando su compra',

    'mg.messages.registerSuccess': 'Registración éxitosa',
    'mg.messages.registerError': 'No se pudo registrar',
    'delete': 'Eliminar',
    'mg.javascript1': 'Javascript deshabilitado',
    'mg.javascript2': 'Este sitio fue diseñado para utilizar Javascript. Habilítelo para utilizar todas sus ventajas.',
    'sureResume': 'Está seguro que desea reanudar esta publicación?',
    'sureActivate': 'Está seguro que desea activar esta publicación?',
    'surePause': 'Está seguro que desea pausar esta publicación?'
  };
});