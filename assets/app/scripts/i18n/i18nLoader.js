'use strict';
define(function() {
	// Add languages here
	var userLang, listOfSupportedLanguages = ['en', 'es'];

	// to avoid being called in non browser environments
	if (typeof navigator === 'object') {
		userLang = navigator.language || navigator.userLanguage;
		userLang = userLang.split('-')[0];
	}

	// Set English as default language
	if (userLang === undefined || listOfSupportedLanguages.indexOf(userLang) < 0) {
		userLang = 'en';
	}
	return {
		load: function (name, require, load) {
			require(['i18n/messages_' + userLang], function (value) {
				load(value);
				return value;
			});
		}
	};
});
