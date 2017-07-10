define([ "siglasApp" ], function(siglasApp) {
    siglasApp.service("ErrorService", function() {
        var ErrorService = {};
        return ErrorService.User = {}, ErrorService.User.UserNotFoundError = "Usuario no encontrado", 
        ErrorService.User.IncorrectPasswordError = "Contraseña Incorrecta", ErrorService.User.UserLoggedInError = "Ya ha iniciado sesión", 
        ErrorService.User.UserDisabledError = "Contactese con el administrador para iniciar sesión", 
        ErrorService;
    });
});