module.exports = function (grunt) {

	var destinationDirectory = '../webapp/src/main/webapp/resources/';

	grunt.initConfig({
		// setup grunt
		pkg: grunt.file.readJSON('package.json'),

		notify_hooks: {
			options: {
				title: 'TP PAW 2016 Q2',
				duration: 2
			}
		},

		// check validity
		jshint: {
			files: ['Gruntfile.js', 'src/js/*.js'],
			options: {
				multistr: true,
				globals: {
					jQuery: true
				}
			}
		},

		copy: {
			main: {
				files: [{
					// jquery
					expand: true,
					dot: true,
					cwd: 'bower_components/jquery/dist',
					src: ['jquery.min.js', 'jquery.js'],
					dest: destinationDirectory+'js/lib/'
				}, {
					// bootstrap fonts
					expand: true,
					dot: true,
					cwd: 'bower_components/bootstrap/fonts/',
					src: ['*.*'],
					dest: destinationDirectory+'fonts/'
				}, {
					// bootstrap stylesheets
					expand: true,
					dot: true,
					cwd: 'bower_components/bootstrap/dist/css',
					src: ['bootstrap.min.css', 'bootstrap.css'],
					dest: destinationDirectory+'css'
				}, {
					// bootstrap theme
					expand: true,
          dot: true,
          cwd: 'bower_components/bootstrap/dist/css',
          src: ['bootstrap-theme.min.css', 'bootstrap-theme.css'],
          dest: destinationDirectory+'css/'
				}, {
					// bootstrap javascripts
					expand: true,
					dot: true,
					cwd: 'bower_components/bootstrap/dist/js',
					src: ['bootstrap.min.js', 'bootstrap.js'],
					dest: destinationDirectory+'js/lib/'
				}, {
					// fa fonts
					expand: true,
					dot: true,
					cwd: 'bower_components/font-awesome/fonts',
					src: ['*.*'],
					dest: destinationDirectory+'fonts/'
				}, {
					// fa stylesheets
					expand: true,
					dot: true,
					cwd: 'bower_components/font-awesome/css',
					src: ['font-awesome.min.css', 'font-awesome.css'],
					dest: destinationDirectory+'css/'
				}, {
					// async
					expand: true,
    			dot: true,
    			cwd: 'bower_components/async/dist',
    			src: ['*.js'],
    			dest: destinationDirectory+'js/lib/'
				}, {
					// underscore
					expand: true,
    			dot: true,
    			cwd: 'bower_components/underscore',
    			src: ['*.js'],
    			dest: destinationDirectory+'js/lib/'
				}, {
					// bootbox
					expand: true,
    			dot: true,
    			cwd: 'bower_components/bootbox.js',
    			src: ['*.js'],
    			dest: destinationDirectory+'js/lib/'
				}, {
					// q
					expand: true,
    			dot: true,
    			cwd: 'bower_components/q',
    			src: ['q.js'],
    			dest: destinationDirectory+'js/lib/'
				}, {
					// moment
					expand: true,
    			dot: true,
    			cwd: 'bower_components/moment/min',
    			src: ['moment-with-locales.js', 'moment-with-locales.min.js'],
    			dest: destinationDirectory+'js/lib/'
				}, {
					// animate.css
					expand: true,
    			dot: true,
    			cwd: 'bower_components/animate.css',
    			src: ['*.css'],
    			dest: destinationDirectory+'css/'
				}, {
					// jquery easing
					expand: true,
					dot: true,
					cwd: 'bower_components/jquery.easing',
					src: ['jquery.easing.js', 'jquery.easing.min.js'],
					dest: destinationDirectory+'js/lib/'
				}, {
					// jqBootstrap Validation
					expand: true,
					dot: true,
					cwd: 'bower_components/jqBootstrapValidation/dist',
					src: ['*.js'],
					dest: destinationDirectory+'js/lib/'
				}, {
					// bootbox
					expand: true,
					dot: true,
					cwd: 'bower_components/bootbox.js',
					src: ['*.js'],
					dest: destinationDirectory+'js/lib/'
				}, {
					// dropzone js
					expand: true,
					dot: true,
					cwd: 'bower_components/dropzone/dist/',
					src: ['dropzone.js'],
					dest: destinationDirectory+'js/lib/'
				}, {
					// dropzone css
					expand: true,
					dot: true,
					cwd: 'bower_components/dropzone/dist/',
					src: ['*.css'],
					dest: destinationDirectory+'css/'
				}, {
					// jquery cookie
					expand: true,
					dot: true,
					cwd: 'bower_components/jquery.cookie/',
					src: ['*.js'],
					dest: destinationDirectory+'js/lib/'
				}, {
					// jquery-treegrid js
					expand: true,
					dot: true,
					cwd: 'bower_components/jquery-treegrid/js',
					src: ['jquery.treegrid.js','jquery.treegrid.bootstrap3.js'],
					dest: destinationDirectory+'js/lib/'
				}, {
					// jquery-treegrid css
					expand: true,
					dot: true,
					cwd: 'bower_components/jquery-treegrid/css',
					src: ['*.css'],
					dest: destinationDirectory+'css/'
				}, {
					// my js files
					expand: true,
    			dot: true,
    			cwd: 'src/js',
    			src: ['*.js'],
    			dest: destinationDirectory+'js/'
				}]
			}
		},

		// minify my sources
		uglify: {
			dist: {
				files: grunt.file.expandMapping([destinationDirectory+'js/*.js', '!'+destinationDirectory+'js/*.min.js'], '', {
					rename: function (destBase, destPath) {
						return destBase+destPath.replace('.js', '.min.js');
					}
				})
			}
		},

		//sass to css
		sass: {
			options: {
				sorceMap: false
			},
			dist: {
				files: [{
					expand: true,
					cwd: 'src/styles',
					src: ['*.scss','*.sass'],
					dest: destinationDirectory+'css/',
					// dest: destinationDirectory+'css',
					ext: '.css'
				}]
			}
		},

		cssmin: {
			target: {
				files: [{
					expand: true,
					cwd: destinationDirectory+'css/',
					// cwd: destinationDirectory+'css',
					src: ['*.css', '!*.min.css'],
					dest: destinationDirectory+'css/',
					// dest: destinationDirectory+'css',
					ext: '.min.css'
				}]
			}
		},

		clean: {
			options: {
				force: true
			},
			js: [destinationDirectory+'js/**/*.js'],
			css: [destinationDirectory+'css/*.css'],
			fonts: [destinationDirectory+'fonts/**/*'],
			html: [destinationDirectory+'*.html']
		},

		livescript: {
			options: {
				header: false,
				bare: true
			},
			src: {
				files: [{
					expand: true,
					cwd: 'src/ls',
					src: ['*.ls'],
					dest: destinationDirectory+'js/',
					// dest: destinationDirectory+'js',
					ext: '.js'
				}]
			}
		},

		notify: {
			dist: {
				options: {
					title: 'TP PAW 2016 Q2',
					duration: 2,
					message: 'Success'
				}
			}
		},

		watch: {
			dev: {
				files: ['Gruntfile.js', 'src/js/*.js', 'src/styles/*.sass', 'src/styles/*.scss', 'src/views/**/*.jade', 'src/ls/*.ls'],
				tasks: [
					// 'clean',
					'jshint',
					'copy:main',
					'sass:dist', // compile
					'livescript',
					'notify'
					// 'concat:dev'
				],
				options: {
					atBegin: true
				}
			},
			min: {
				files: ['Gruntfile.js', 'src/js/*.js', 'src/styles/*.sass', 'src/styles/*.scss', 'src/views/**/*.jade', 'src/ls/*.ls'],
				tasks: [
					'clean',
					'jshint',
					'copy:main',
					'sass:dist', // compile
					'livescript',
					'uglify:dist',
					'cssmin'//,
					// 'concat:min'
				],
				options: {
					atBegin: true
				}
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-cssmin');
	grunt.loadNpmTasks('grunt-sass');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-livescript');
	grunt.loadNpmTasks('grunt-notify');

	grunt.task.run('notify_hooks');

	grunt.registerTask('default', ['jshint', 'copy:main', 'sass:dist', 'livescript']);
	grunt.registerTask('dev', ['watch:dev']);
	grunt.registerTask('min', ['watch:min']);

};