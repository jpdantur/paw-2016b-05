module.exports = function (grunt) {

	var moment = require('moment');

	moment.locale('es');

	grunt.initConfig({
		// setup grunt
		pkg: grunt.file.readJSON('package.json'),

		notify_hooks: {
			options: {
				title: 'TP PAW 2016 Q2',
				duration: 2
			}
		},

		// install bower
		bower: {
			install: {
				options: {
					install: true,
					copy: false,
					targetDir: 'dist/lib',
					cleanTargetDir: true
				}
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
					dest: 'dist/js/lib/'
					// dest: 'dist/lib/js/'
				}, {
					// bootstrap fonts
					expand: true,
					dot: true,
					cwd: 'bower_components/bootstrap/fonts/',
					src: ['*.*'],
					dest: 'dist/fonts/'
					// dest: 'dist/fonts/'
				}, {
					// bootstrap stylesheets
					expand: true,
					dot: true,
					cwd: 'bower_components/bootstrap/dist/css',
					src: ['bootstrap.min.css', 'bootstrap.css'],
					dest: 'dist/css'
					// dest: 'dist/lib/css/'
				}, {
					// bootstrap theme
					expand: true,
          			dot: true,
          			cwd: 'bower_components/bootstrap/dist/css',
          			src: ['bootstrap-theme.min.css', 'bootstrap-theme.css'],
          			dest: 'dist/css/'
          			// dest: 'dist/lib/css/'
				}, {
					// bootstrap javascripts
					expand: true,
					dot: true,
					cwd: 'bower_components/bootstrap/dist/js',
					src: ['bootstrap.min.js', 'bootstrap.js'],
					dest: 'dist/js/lib/'
					// dest: 'dist/lib/js/'
				}, {
					// fa fonts
					expand: true,
					dot: true,
					cwd: 'bower_components/font-awesome/fonts',
					src: ['*.*'],
					dest: 'dist/fonts/'
					// dest: 'dist/fonts/'
				}, {
					// fa stylesheets
					expand: true,
					dot: true,
					cwd: 'bower_components/font-awesome/css',
					src: ['font-awesome.min.css', 'font-awesome.css'],
					dest: 'dist/css/'
					// dest: 'dist/lib/css/'
				}, {
					// async
					expand: true,
          			dot: true,
          			cwd: 'bower_components/async/dist',
          			src: ['*.js'],
          			dest: 'dist/js/lib/'
          			// dest: 'dist/lib/js/'
				}, {
					// underscore
					expand: true,
          			dot: true,
          			cwd: 'bower_components/underscore',
          			src: ['*.js'],
          			dest: 'dist/js/lib/'
          			// dest: 'dist/lib/js/'
				}, {
					// bootbox
					expand: true,
          			dot: true,
          			cwd: 'bower_components/bootbox.js',
          			src: ['*.js'],
          			dest: 'dist/js/lib/'
          			// dest: 'dist/lib/js/'
				}, {
					// q
					expand: true,
          			dot: true,
          			cwd: 'bower_components/q',
          			src: ['q.js'],
          			dest: 'dist/js/lib/'
          			// dest: 'dist/lib/js/'
				}, {
					// moment
					expand: true,
          			dot: true,
          			cwd: 'bower_components/moment/min',
          			src: ['moment-with-locales.js', 'moment-with-locales.min.js'],
          			dest: 'dist/js/lib/'
          			// dest: 'dist/lib/js/'
				}, {
					// animate.css
					expand: true,
          			dot: true,
          			cwd: 'bower_components/animate.css',
          			src: ['*.css'],
          			dest: 'dist/css/'
          			// dest: 'dist/lib/js/'
				}, {
					// jquery easing
					expand: true,
					dot: true,
					cwd: 'bower_components/jquery-easing-original',
					src: ['jquery.easing.js', 'jquery.easing.min.js'],
					dest: 'dist/js/lib/'
					// dest: 'dist/lib/js'
				}, {
					// jqBootstrap Validation
					expand: true,
					dot: true,
					cwd: 'bower_components/jqBootstrapValidation/dist',
					src: ['*.js'],
					dest: 'dist/js/lib/'
					// dest: 'dist/lib/js'
				}, {
					// bootbox
					expand: true,
					dot: true,
					cwd: 'bower_components/bootbox.js',
					src: ['*.js'],
					dest: 'dist/js/lib/'
					// dest: 'dist/lib/js'
				}, {
					// my js files
					expand: true,
          			dot: true,
          			cwd: 'src/js',
          			src: ['*.js'],
          			dest: 'dist/js/'
          			// dest: 'dist/js/'
				}]
			}
		},

		// minify my sources
		uglify: {
			dist: {
				files: grunt.file.expandMapping(['dist/js/*.js', '!dist/js/*.min.js'], '', {
				// files: grunt.file.expandMapping(['dist/js/*.js', '!dist/js/*.min.js'], '', {
					rename: function (destBase, destPath) {
						return destBase+destPath.replace('.js', '.min.js');
					}
				})
			}
		},

		concat: {
			dev: {
				files: {
					'dist/js/tp-paw-2016-q2.js': [
						'dist/lib/js/jquery.js',
						'dist/lib/js/jquery.easing.js',
						'dist/lib/js/bootstrap.js',
						'dist/lib/js/underscore.js',
						'dist/js/*.js',
						'!dist/js/*.min.js',
						'!dist/js/tp-paw-2016-q2.js'
					],
					'dist/css/tp-paw-2016-q2.css': [
						'dist/lib/css/*.css',
						'!dist/lib/css/*.min.css',
						'dist/css/*.css',
						'!dist/css/*.min.css',
						'!dist/css/tp-paw-2016-q2.css'
					],
				}
			},
			min: {
				files: {
					'dist/js/tp-paw-2016-q2.min.js': [
						'dist/lib/js/jquery.min.js',
						'dist/lib/js/jquery.easing.min.js',
						'dist/lib/js/bootstrap.min.js',
						'dist/lib/js/underscore-min.js',
						'dist/js/*.min.js',
						'!dist/js/tp-paw-2016-q2.min.js'
					],
					'dist/css/tp-paw-2016-q2.min.css': [
						'dist/lib/css/*.min.css',
						'dist/css/*.min.css',
						'!dist/css/tp-paw-2016-q2.min.css'
					]
				}
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
					dest: 'dist/css/',
					// dest: 'dist/css',
					ext: '.css'
				}]
			}
		},

		cssmin: {
			target: {
				files: [{
					expand: true,
					cwd: 'dist/css/',
					// cwd: 'dist/css',
					src: ['*.css', '!*.min.css'],
					dest: 'dist/css/',
					// dest: 'dist/css',
					ext: '.min.css'
				}]
			}
		},

		// clean: {
		// 	temp: {
		// 		src: [ 'tmp' ]
		// 	}
		// },

		pug: {
			dev: {
				options: {
					pretty: true,
					data: {
						dev: true,
						moment: moment
					}
				},
				files: [{
					expand: true,
					cwd: 'src/views',
					src: ['*.jade'],
					// dest: 'dist/',
					dest: 'dist/',
					ext: '.html'
				}]
			},
			min: {
				options: {
					data: {
						dev: false,
						moment: moment
					}
				},
				files: [{
					expand: true,
					cwd: 'src/views',
					src: ['*.jade'],
					// dest: 'dist/',
					dest: 'dist/',
					ext: '.html'
				}]
			}
		},

		clean: {
			options: {
				force: true
			},
			js: ['dist/js/**/*.js'],
			css: ['dist/css/*.css'],
			fonts: ['dist/fonts/**/*'],
			html: ['dist/*.html']
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
					dest: 'dist/js/',
					// dest: 'dist/js',
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
					'pug:dev', // compile
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
					'pug:min', // compile
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
	grunt.loadNpmTasks('grunt-contrib-pug');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-cssmin');
	// grunt.loadNpmTasks('grunt-contrib-sass');
	grunt.loadNpmTasks('grunt-sass');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-bower-task');
	grunt.loadNpmTasks('grunt-livescript');
	grunt.loadNpmTasks('grunt-notify');

	grunt.task.run('notify_hooks');

	grunt.registerTask('dev', ['bower', 'watch:dev']);
	grunt.registerTask('min', ['bower', 'watch:min']);

};