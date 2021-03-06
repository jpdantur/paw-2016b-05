/* global paths */
'use strict';
require.config({
	baseUrl: 'scripts',
	paths: {
		affix: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/affix',
		alert: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/alert',
		angular: '../../bower_components/angular/angular',
		'angular-route': '../../bower_components/angular-route/angular-route',
		'angular-translate': '../../bower_components/angular-translate/angular-translate',
		button: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/button',
		bootstrap: '../../bower_components/bootstrap/dist/js/bootstrap',
		carousel: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/carousel',
		collapse: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/collapse',
		dropdown: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/dropdown',
		'es5-shim': '../../bower_components/es5-shim/es5-shim',
		jquery: '../../bower_components/jquery/dist/jquery',
		json3: '../../bower_components/json3/lib/json3',
		modal: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/modal',
		moment: '../../bower_components/moment/min/moment-with-locales',
		popover: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/popover',
		requirejs: '../../bower_components/requirejs/require',
		scrollspy: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/scrollspy',
		tab: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tab',
		tooltip: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tooltip',
		transition: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/transition',
		'bootstrap-sass-official': '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap',
		'angular-animate': '../../bower_components/angular-animate/angular-animate',
		'angular-bootstrap': '../../bower_components/angular-bootstrap/ui-bootstrap-tpls',
		'angular-sanitize': '../../bower_components/angular-sanitize/angular-sanitize',
		lodash: '../../bower_components/lodash/lodash',
		'angular-local-storage': '../../bower_components/angular-local-storage/dist/angular-local-storage',
		'angular-toastr': '../../bower_components/angular-toastr/dist/angular-toastr.tpls',
		'angular-jwt': '../../bower_components/angular-jwt/dist/angular-jwt',
		async: '../../bower_components/async/dist/async',
		'angular-moment': '../../bower_components/angular-moment/angular-moment',
		bootbox: '../../bower_components/bootbox.js/bootbox',
		'jquery.easing': '../../bower_components/jquery.easing/jquery.easing',
		ngBootbox: '../../bower_components/ngBootbox/dist/ngBootbox',
		'ng-file-upload': '../../bower_components/ng-file-upload/ng-file-upload',
		'angular-socket-io': '../../bower_components/angular-socket-io/socket',
		'angular-md5': '../../bower_components/angular-md5/angular-md5'
	},
	shim: {
		angular: {
			deps: [
				'jquery'
			]
		},
		'angular-route': {
			deps: [
				'angular'
			]
		},
		'angular-sanitize': {
			deps: [
				'angular'
			]
		},
		bootstrap: {
			deps: [
				'jquery',
				'modal'
			]
		},
		modal: {
			deps: [
				'jquery'
			]
		},
		tooltip: {
			deps: [
				'jquery'
			]
		},
		'angular-translate': {
			deps: [
				'angular'
			]
		},
		'angular-local-storage': {
			deps: [
				'angular'
			]
		},
		'angular-animate': {
			deps: [
				'angular'
			]
		},
		'angular-toastr': {
			deps: [
				'angular'
			]
		},
		'angular-jwt': {
			deps: [
				'angular'
			]
		},
		'angular-bootstrap': {
			deps: [
				'angular'
			]
		},
		'angular-socket-io': {
			deps: [
				'angular'
			]
		},
		'ng-file-upload': {
			deps: [
				'angular'
			]
		},
		'angular-moment': {
			deps: [
				'angular',
				'moment'
			]
		},
		ngBootbox: {
			deps: [
				'bootbox',
				'angular',
				'jquery',
				'bootstrap'
			]
		},
		'angular-md5': {
			deps: [
				'angular'
			]
		}
	},
	packages: [

	]
});

if (paths) {
	require.config({
		paths: paths
	});
}

require([
	'angular',
	'siglasApp',
	'controllers/IndexCtrl',
	'services/AuthService',
	'services/ItemService',
	'services/FavouritesService'
], function() {
	angular.bootstrap(document, ['siglasApp']);
});
