angular.module("btford.socket-io", []).provider("socketFactory", function() {
    "use strict";
    this.$get = [ "$rootScope", "$timeout", function($rootScope, $timeout) {
        var asyncAngularify = function(socket, callback) {
            return callback ? function() {
                var args = arguments;
                $timeout(function() {
                    callback.apply(socket, args);
                }, 0);
            } : angular.noop;
        };
        return function(options) {
            options = options || {};
            var socket = options.ioSocket || io.connect(), prefix = void 0 === options.prefix ? "socket:" : options.prefix, defaultScope = options.scope || $rootScope, addListener = function(eventName, callback) {
                socket.on(eventName, callback.__ng = asyncAngularify(socket, callback));
            };
            return {
                on: addListener,
                addListener: addListener,
                once: function(eventName, callback) {
                    socket.once(eventName, callback.__ng = asyncAngularify(socket, callback));
                },
                emit: function(eventName, data, callback) {
                    var lastIndex = arguments.length - 1, callback = arguments[lastIndex];
                    return "function" == typeof callback && (callback = asyncAngularify(socket, callback), 
                    arguments[lastIndex] = callback), socket.emit.apply(socket, arguments);
                },
                removeListener: function(ev, fn) {
                    return fn && fn.__ng && (arguments[1] = fn.__ng), socket.removeListener.apply(socket, arguments);
                },
                removeAllListeners: function() {
                    return socket.removeAllListeners.apply(socket, arguments);
                },
                disconnect: function(close) {
                    return socket.disconnect(close);
                },
                connect: function() {
                    return socket.connect();
                },
                forward: function(events, scope) {
                    events instanceof Array == 0 && (events = [ events ]), scope || (scope = defaultScope), 
                    events.forEach(function(eventName) {
                        var prefixedEvent = prefix + eventName, forwardBroadcast = asyncAngularify(socket, function() {
                            Array.prototype.unshift.call(arguments, prefixedEvent), scope.$broadcast.apply(scope, arguments);
                        });
                        scope.$on("$destroy", function() {
                            socket.removeListener(eventName, forwardBroadcast);
                        }), socket.on(eventName, forwardBroadcast);
                    });
                }
            };
        };
    } ];
});