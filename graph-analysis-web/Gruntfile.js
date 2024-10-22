/*global module:false*/
module.exports = function (grunt) {

    // Project configuration.
    grunt.initConfig({
        // Task configuration.
        jshint: {
            options: {
                curly: true,
                eqeqeq: true,
                immed: true,
                latedef: true,
                newcap: true,
                noarg: true,
                sub: true,
                undef: true,
                unused: true,
                boss: true,
                eqnull: true,
                browser: true,
                globals: {
                    jQuery: true
                }
            },
            gruntfile: {
                src: 'Gruntfile.js'
            },
            lib_test: {
                src: ['lib/**/*.js', 'test/**/*.js']
            }
        },
        qunit: {
            files: ['test/**/*.html']
        },
        watch: {
            gruntfile: {
                files: '<%= jshint.gruntfile.src %>',
                tasks: ['jshint:gruntfile']
            },
            lib_test: {
                files: '<%= jshint.lib_test.src %>',
                tasks: ['jshint:lib_test', 'qunit']
            },
            sass: {
                files: ['src/main/scss/**/*.{scss,sass}'],
                tasks: ['sass:server']
            }
        },
        ngconstant: {
            options: {
                name: 'ewPortalApp',
                deps: false,
                wrap: '"use strict";\n// DO NOT EDIT THIS FILE, EDIT THE GRUNT TASK NGCONSTANT SETTINGS INSTEAD WHICH GENERATES THIS FILE\n{%= __ngModule %}'
            },
            dev: {
                options: {
                    dest: 'src/main/resources/static/scripts/constants.js'
                },
                constants: {
                    ENV: 'dev',
                    hostName: 'localhost:9001'
                }
            }
        },
        sass: {
            options: {
                style: 'expanded'
            },
            server: {
                files: [{
                    expand: true,
                    cwd: 'src/main/scss',
                    src: ['*.scss'],
                    dest: 'src/main/resources/static/assets/styles',
                    ext: '.css'
                }]
            }
        }
    });

    // These plugins provide necessary tasks.
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-watch');

    // SCSS
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-ng-constant');

    // Default task.
    grunt.registerTask('default', ['jshint', 'sass:server']);//, 'qunit'

    grunt.registerTask('serve', [
        'sass:server',
        'watch'
    ]);

    grunt.registerTask('build', [
        'ngconstant:prod',
        'sass:server'
    ]);

    grunt.registerTask('buildDev', [
        'ngconstant:dev'
    ]);

};
