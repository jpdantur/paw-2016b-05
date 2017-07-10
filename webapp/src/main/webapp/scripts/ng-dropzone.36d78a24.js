!function(root) {
    "use strict";
    function factory(angular, Dropzone) {
        angular.module("thatisuday.dropzone", []).provider("dropzoneOps", function() {
            var defOps = {};
            return {
                setOptions: function(newOps) {
                    angular.extend(defOps, newOps);
                },
                $get: function() {
                    return defOps;
                }
            };
        }).directive("ngDropzone", [ "$timeout", "dropzoneOps", function($timeout, dropzoneOps) {
            return {
                restrict: "AE",
                template: "<div></div>",
                replace: !0,
                scope: {
                    options: "=?",
                    callbacks: "=?",
                    methods: "=?"
                },
                link: function(scope, iElem, iAttr) {
                    scope.options = scope.options || {};
                    var initOps = angular.extend({}, dropzoneOps, scope.options), dropzone = new Dropzone(iElem[0], initOps);
                    scope.methods = scope.methods || {}, scope.methods.getDropzone = function() {
                        return dropzone;
                    }, scope.methods.getAllFiles = function() {
                        return dropzone.files;
                    };
                    var controlMethods = [ "removeFile", "removeAllFiles", "processQueue", "getAcceptedFiles", "getRejectedFiles", "getQueuedFiles", "getUploadingFiles", "disable", "enable", "confirm", "createThumbnailFromUrl" ];
                    if (angular.forEach(controlMethods, function(methodName) {
                        scope.methods[methodName] = function() {
                            dropzone[methodName].apply(dropzone, arguments), scope.$$phase || scope.$root.$$phase || scope.$apply();
                        };
                    }), scope.callbacks) {
                        var callbackMethods = [ "drop", "dragstart", "dragend", "dragenter", "dragover", "dragleave", "addedfile", "removedfile", "thumbnail", "error", "processing", "uploadprogress", "sending", "success", "complete", "canceled", "maxfilesreached", "maxfilesexceeded", "processingmultiple", "sendingmultiple", "successmultiple", "completemultiple", "canceledmultiple", "totaluploadprogress", "reset", "queuecomplete" ];
                        angular.forEach(callbackMethods, function(method) {
                            var callback = scope.callbacks[method] || angular.noop;
                            dropzone.on(method, function() {
                                callback.apply(null, arguments), scope.$$phase || scope.$root.$$phase || scope.$apply();
                            });
                        });
                    }
                }
            };
        } ]);
    }
    "object" == typeof module && module.exports ? module.exports = factory(require("angular"), require("dropzone")) : "function" == typeof define && define.amd ? define([ "angular", "dropzone" ], factory) : factory(root.angular, root.Dropzone);
}(this);