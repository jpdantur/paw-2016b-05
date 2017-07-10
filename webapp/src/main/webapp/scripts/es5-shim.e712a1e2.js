!function(root, factory) {
    "use strict";
    "function" == typeof define && define.amd ? define(factory) : "object" == typeof exports ? module.exports = factory() : root.returnExports = factory();
}(this, function() {
    var isCallable, isRegex, $Array = Array, ArrayPrototype = $Array.prototype, $Object = Object, ObjectPrototype = $Object.prototype, $Function = Function, FunctionPrototype = $Function.prototype, $String = String, StringPrototype = $String.prototype, $Number = Number, NumberPrototype = $Number.prototype, array_slice = ArrayPrototype.slice, array_splice = ArrayPrototype.splice, array_push = ArrayPrototype.push, array_unshift = ArrayPrototype.unshift, array_concat = ArrayPrototype.concat, array_join = ArrayPrototype.join, call = FunctionPrototype.call, apply = FunctionPrototype.apply, max = Math.max, min = Math.min, to_string = ObjectPrototype.toString, hasToStringTag = "function" == typeof Symbol && "symbol" == typeof Symbol.toStringTag, fnToStr = Function.prototype.toString, constructorRegex = /^\s*class /, isES6ClassFn = function(value) {
        try {
            var fnStr = fnToStr.call(value), singleStripped = fnStr.replace(/\/\/.*\n/g, ""), multiStripped = singleStripped.replace(/\/\*[.\s\S]*\*\//g, ""), spaceStripped = multiStripped.replace(/\n/gm, " ").replace(/ {2}/g, " ");
            return constructorRegex.test(spaceStripped);
        } catch (e) {
            return !1;
        }
    }, tryFunctionObject = function(value) {
        try {
            return !isES6ClassFn(value) && (fnToStr.call(value), !0);
        } catch (e) {
            return !1;
        }
    }, fnClass = "[object Function]", genClass = "[object GeneratorFunction]", isCallable = function(value) {
        if (!value) return !1;
        if ("function" != typeof value && "object" != typeof value) return !1;
        if (hasToStringTag) return tryFunctionObject(value);
        if (isES6ClassFn(value)) return !1;
        var strClass = to_string.call(value);
        return strClass === fnClass || strClass === genClass;
    }, regexExec = RegExp.prototype.exec, tryRegexExec = function(value) {
        try {
            return regexExec.call(value), !0;
        } catch (e) {
            return !1;
        }
    }, regexClass = "[object RegExp]";
    isRegex = function(value) {
        return "object" == typeof value && (hasToStringTag ? tryRegexExec(value) : to_string.call(value) === regexClass);
    };
    var isString, strValue = String.prototype.valueOf, tryStringObject = function(value) {
        try {
            return strValue.call(value), !0;
        } catch (e) {
            return !1;
        }
    }, stringClass = "[object String]";
    isString = function(value) {
        return "string" == typeof value || "object" == typeof value && (hasToStringTag ? tryStringObject(value) : to_string.call(value) === stringClass);
    };
    var supportsDescriptors = $Object.defineProperty && function() {
        try {
            var obj = {};
            $Object.defineProperty(obj, "x", {
                enumerable: !1,
                value: obj
            });
            for (var _ in obj) return !1;
            return obj.x === obj;
        } catch (e) {
            return !1;
        }
    }(), defineProperties = function(has) {
        var defineProperty;
        return defineProperty = supportsDescriptors ? function(object, name, method, forceAssign) {
            !forceAssign && name in object || $Object.defineProperty(object, name, {
                configurable: !0,
                enumerable: !1,
                writable: !0,
                value: method
            });
        } : function(object, name, method, forceAssign) {
            !forceAssign && name in object || (object[name] = method);
        }, function(object, map, forceAssign) {
            for (var name in map) has.call(map, name) && defineProperty(object, name, map[name], forceAssign);
        };
    }(ObjectPrototype.hasOwnProperty), isPrimitive = function(input) {
        var type = typeof input;
        return null === input || "object" !== type && "function" !== type;
    }, isActualNaN = $Number.isNaN || function(x) {
        return x !== x;
    }, ES = {
        ToInteger: function(num) {
            var n = +num;
            return isActualNaN(n) ? n = 0 : 0 !== n && n !== 1 / 0 && n !== -(1 / 0) && (n = (n > 0 || -1) * Math.floor(Math.abs(n))), 
            n;
        },
        ToPrimitive: function(input) {
            var val, valueOf, toStr;
            if (isPrimitive(input)) return input;
            if (valueOf = input.valueOf, isCallable(valueOf) && (val = valueOf.call(input), 
            isPrimitive(val))) return val;
            if (toStr = input.toString, isCallable(toStr) && (val = toStr.call(input), isPrimitive(val))) return val;
            throw new TypeError();
        },
        ToObject: function(o) {
            if (null == o) throw new TypeError("can't convert " + o + " to object");
            return $Object(o);
        },
        ToUint32: function(x) {
            return x >>> 0;
        }
    }, Empty = function() {};
    defineProperties(FunctionPrototype, {
        bind: function(that) {
            var target = this;
            if (!isCallable(target)) throw new TypeError("Function.prototype.bind called on incompatible " + target);
            for (var bound, args = array_slice.call(arguments, 1), binder = function() {
                if (this instanceof bound) {
                    var result = apply.call(target, this, array_concat.call(args, array_slice.call(arguments)));
                    return $Object(result) === result ? result : this;
                }
                return apply.call(target, that, array_concat.call(args, array_slice.call(arguments)));
            }, boundLength = max(0, target.length - args.length), boundArgs = [], i = 0; i < boundLength; i++) array_push.call(boundArgs, "$" + i);
            return bound = $Function("binder", "return function (" + array_join.call(boundArgs, ",") + "){ return binder.apply(this, arguments); }")(binder), 
            target.prototype && (Empty.prototype = target.prototype, bound.prototype = new Empty(), 
            Empty.prototype = null), bound;
        }
    });
    var owns = call.bind(ObjectPrototype.hasOwnProperty), toStr = call.bind(ObjectPrototype.toString), arraySlice = call.bind(array_slice), arraySliceApply = apply.bind(array_slice), strSlice = call.bind(StringPrototype.slice), strSplit = call.bind(StringPrototype.split), strIndexOf = call.bind(StringPrototype.indexOf), pushCall = call.bind(array_push), isEnum = call.bind(ObjectPrototype.propertyIsEnumerable), arraySort = call.bind(ArrayPrototype.sort), isArray = $Array.isArray || function(obj) {
        return "[object Array]" === toStr(obj);
    }, hasUnshiftReturnValueBug = 1 !== [].unshift(0);
    defineProperties(ArrayPrototype, {
        unshift: function() {
            return array_unshift.apply(this, arguments), this.length;
        }
    }, hasUnshiftReturnValueBug), defineProperties($Array, {
        isArray: isArray
    });
    var boxedString = $Object("a"), splitString = "a" !== boxedString[0] || !(0 in boxedString), properlyBoxesContext = function(method) {
        var properlyBoxesNonStrict = !0, properlyBoxesStrict = !0, threwException = !1;
        if (method) try {
            method.call("foo", function(_, __, context) {
                "object" != typeof context && (properlyBoxesNonStrict = !1);
            }), method.call([ 1 ], function() {
                "use strict";
                properlyBoxesStrict = "string" == typeof this;
            }, "x");
        } catch (e) {
            threwException = !0;
        }
        return !!method && !threwException && properlyBoxesNonStrict && properlyBoxesStrict;
    };
    defineProperties(ArrayPrototype, {
        forEach: function(callbackfn) {
            var T, object = ES.ToObject(this), self = splitString && isString(this) ? strSplit(this, "") : object, i = -1, length = ES.ToUint32(self.length);
            if (arguments.length > 1 && (T = arguments[1]), !isCallable(callbackfn)) throw new TypeError("Array.prototype.forEach callback must be a function");
            for (;++i < length; ) i in self && ("undefined" == typeof T ? callbackfn(self[i], i, object) : callbackfn.call(T, self[i], i, object));
        }
    }, !properlyBoxesContext(ArrayPrototype.forEach)), defineProperties(ArrayPrototype, {
        map: function(callbackfn) {
            var T, object = ES.ToObject(this), self = splitString && isString(this) ? strSplit(this, "") : object, length = ES.ToUint32(self.length), result = $Array(length);
            if (arguments.length > 1 && (T = arguments[1]), !isCallable(callbackfn)) throw new TypeError("Array.prototype.map callback must be a function");
            for (var i = 0; i < length; i++) i in self && ("undefined" == typeof T ? result[i] = callbackfn(self[i], i, object) : result[i] = callbackfn.call(T, self[i], i, object));
            return result;
        }
    }, !properlyBoxesContext(ArrayPrototype.map)), defineProperties(ArrayPrototype, {
        filter: function(callbackfn) {
            var value, T, object = ES.ToObject(this), self = splitString && isString(this) ? strSplit(this, "") : object, length = ES.ToUint32(self.length), result = [];
            if (arguments.length > 1 && (T = arguments[1]), !isCallable(callbackfn)) throw new TypeError("Array.prototype.filter callback must be a function");
            for (var i = 0; i < length; i++) i in self && (value = self[i], ("undefined" == typeof T ? callbackfn(value, i, object) : callbackfn.call(T, value, i, object)) && pushCall(result, value));
            return result;
        }
    }, !properlyBoxesContext(ArrayPrototype.filter)), defineProperties(ArrayPrototype, {
        every: function(callbackfn) {
            var T, object = ES.ToObject(this), self = splitString && isString(this) ? strSplit(this, "") : object, length = ES.ToUint32(self.length);
            if (arguments.length > 1 && (T = arguments[1]), !isCallable(callbackfn)) throw new TypeError("Array.prototype.every callback must be a function");
            for (var i = 0; i < length; i++) if (i in self && !("undefined" == typeof T ? callbackfn(self[i], i, object) : callbackfn.call(T, self[i], i, object))) return !1;
            return !0;
        }
    }, !properlyBoxesContext(ArrayPrototype.every)), defineProperties(ArrayPrototype, {
        some: function(callbackfn) {
            var T, object = ES.ToObject(this), self = splitString && isString(this) ? strSplit(this, "") : object, length = ES.ToUint32(self.length);
            if (arguments.length > 1 && (T = arguments[1]), !isCallable(callbackfn)) throw new TypeError("Array.prototype.some callback must be a function");
            for (var i = 0; i < length; i++) if (i in self && ("undefined" == typeof T ? callbackfn(self[i], i, object) : callbackfn.call(T, self[i], i, object))) return !0;
            return !1;
        }
    }, !properlyBoxesContext(ArrayPrototype.some));
    var reduceCoercesToObject = !1;
    ArrayPrototype.reduce && (reduceCoercesToObject = "object" == typeof ArrayPrototype.reduce.call("es5", function(_, __, ___, list) {
        return list;
    })), defineProperties(ArrayPrototype, {
        reduce: function(callbackfn) {
            var object = ES.ToObject(this), self = splitString && isString(this) ? strSplit(this, "") : object, length = ES.ToUint32(self.length);
            if (!isCallable(callbackfn)) throw new TypeError("Array.prototype.reduce callback must be a function");
            if (0 === length && 1 === arguments.length) throw new TypeError("reduce of empty array with no initial value");
            var result, i = 0;
            if (arguments.length >= 2) result = arguments[1]; else for (;;) {
                if (i in self) {
                    result = self[i++];
                    break;
                }
                if (++i >= length) throw new TypeError("reduce of empty array with no initial value");
            }
            for (;i < length; i++) i in self && (result = callbackfn(result, self[i], i, object));
            return result;
        }
    }, !reduceCoercesToObject);
    var reduceRightCoercesToObject = !1;
    ArrayPrototype.reduceRight && (reduceRightCoercesToObject = "object" == typeof ArrayPrototype.reduceRight.call("es5", function(_, __, ___, list) {
        return list;
    })), defineProperties(ArrayPrototype, {
        reduceRight: function(callbackfn) {
            var object = ES.ToObject(this), self = splitString && isString(this) ? strSplit(this, "") : object, length = ES.ToUint32(self.length);
            if (!isCallable(callbackfn)) throw new TypeError("Array.prototype.reduceRight callback must be a function");
            if (0 === length && 1 === arguments.length) throw new TypeError("reduceRight of empty array with no initial value");
            var result, i = length - 1;
            if (arguments.length >= 2) result = arguments[1]; else for (;;) {
                if (i in self) {
                    result = self[i--];
                    break;
                }
                if (--i < 0) throw new TypeError("reduceRight of empty array with no initial value");
            }
            if (i < 0) return result;
            do i in self && (result = callbackfn(result, self[i], i, object)); while (i--);
            return result;
        }
    }, !reduceRightCoercesToObject);
    var hasFirefox2IndexOfBug = ArrayPrototype.indexOf && [ 0, 1 ].indexOf(1, 2) !== -1;
    defineProperties(ArrayPrototype, {
        indexOf: function(searchElement) {
            var self = splitString && isString(this) ? strSplit(this, "") : ES.ToObject(this), length = ES.ToUint32(self.length);
            if (0 === length) return -1;
            var i = 0;
            for (arguments.length > 1 && (i = ES.ToInteger(arguments[1])), i = i >= 0 ? i : max(0, length + i); i < length; i++) if (i in self && self[i] === searchElement) return i;
            return -1;
        }
    }, hasFirefox2IndexOfBug);
    var hasFirefox2LastIndexOfBug = ArrayPrototype.lastIndexOf && [ 0, 1 ].lastIndexOf(0, -3) !== -1;
    defineProperties(ArrayPrototype, {
        lastIndexOf: function(searchElement) {
            var self = splitString && isString(this) ? strSplit(this, "") : ES.ToObject(this), length = ES.ToUint32(self.length);
            if (0 === length) return -1;
            var i = length - 1;
            for (arguments.length > 1 && (i = min(i, ES.ToInteger(arguments[1]))), i = i >= 0 ? i : length - Math.abs(i); i >= 0; i--) if (i in self && searchElement === self[i]) return i;
            return -1;
        }
    }, hasFirefox2LastIndexOfBug);
    var spliceNoopReturnsEmptyArray = function() {
        var a = [ 1, 2 ], result = a.splice();
        return 2 === a.length && isArray(result) && 0 === result.length;
    }();
    defineProperties(ArrayPrototype, {
        splice: function(start, deleteCount) {
            return 0 === arguments.length ? [] : array_splice.apply(this, arguments);
        }
    }, !spliceNoopReturnsEmptyArray);
    var spliceWorksWithEmptyObject = function() {
        var obj = {};
        return ArrayPrototype.splice.call(obj, 0, 0, 1), 1 === obj.length;
    }();
    defineProperties(ArrayPrototype, {
        splice: function(start, deleteCount) {
            if (0 === arguments.length) return [];
            var args = arguments;
            return this.length = max(ES.ToInteger(this.length), 0), arguments.length > 0 && "number" != typeof deleteCount && (args = arraySlice(arguments), 
            args.length < 2 ? pushCall(args, this.length - start) : args[1] = ES.ToInteger(deleteCount)), 
            array_splice.apply(this, args);
        }
    }, !spliceWorksWithEmptyObject);
    var spliceWorksWithLargeSparseArrays = function() {
        var arr = new $Array(1e5);
        return arr[8] = "x", arr.splice(1, 1), 7 === arr.indexOf("x");
    }(), spliceWorksWithSmallSparseArrays = function() {
        var n = 256, arr = [];
        return arr[n] = "a", arr.splice(n + 1, 0, "b"), "a" === arr[n];
    }();
    defineProperties(ArrayPrototype, {
        splice: function(start, deleteCount) {
            for (var from, O = ES.ToObject(this), A = [], len = ES.ToUint32(O.length), relativeStart = ES.ToInteger(start), actualStart = relativeStart < 0 ? max(len + relativeStart, 0) : min(relativeStart, len), actualDeleteCount = min(max(ES.ToInteger(deleteCount), 0), len - actualStart), k = 0; k < actualDeleteCount; ) from = $String(actualStart + k), 
            owns(O, from) && (A[k] = O[from]), k += 1;
            var to, items = arraySlice(arguments, 2), itemCount = items.length;
            if (itemCount < actualDeleteCount) {
                k = actualStart;
                for (var maxK = len - actualDeleteCount; k < maxK; ) from = $String(k + actualDeleteCount), 
                to = $String(k + itemCount), owns(O, from) ? O[to] = O[from] : delete O[to], k += 1;
                k = len;
                for (var minK = len - actualDeleteCount + itemCount; k > minK; ) delete O[k - 1], 
                k -= 1;
            } else if (itemCount > actualDeleteCount) for (k = len - actualDeleteCount; k > actualStart; ) from = $String(k + actualDeleteCount - 1), 
            to = $String(k + itemCount - 1), owns(O, from) ? O[to] = O[from] : delete O[to], 
            k -= 1;
            k = actualStart;
            for (var i = 0; i < items.length; ++i) O[k] = items[i], k += 1;
            return O.length = len - actualDeleteCount + itemCount, A;
        }
    }, !spliceWorksWithLargeSparseArrays || !spliceWorksWithSmallSparseArrays);
    var hasStringJoinBug, originalJoin = ArrayPrototype.join;
    try {
        hasStringJoinBug = "1,2,3" !== Array.prototype.join.call("123", ",");
    } catch (e) {
        hasStringJoinBug = !0;
    }
    hasStringJoinBug && defineProperties(ArrayPrototype, {
        join: function(separator) {
            var sep = "undefined" == typeof separator ? "," : separator;
            return originalJoin.call(isString(this) ? strSplit(this, "") : this, sep);
        }
    }, hasStringJoinBug);
    var hasJoinUndefinedBug = "1,2" !== [ 1, 2 ].join(void 0);
    hasJoinUndefinedBug && defineProperties(ArrayPrototype, {
        join: function(separator) {
            var sep = "undefined" == typeof separator ? "," : separator;
            return originalJoin.call(this, sep);
        }
    }, hasJoinUndefinedBug);
    var pushShim = function(item) {
        for (var O = ES.ToObject(this), n = ES.ToUint32(O.length), i = 0; i < arguments.length; ) O[n + i] = arguments[i], 
        i += 1;
        return O.length = n + i, n + i;
    }, pushIsNotGeneric = function() {
        var obj = {}, result = Array.prototype.push.call(obj, void 0);
        return 1 !== result || 1 !== obj.length || "undefined" != typeof obj[0] || !owns(obj, 0);
    }();
    defineProperties(ArrayPrototype, {
        push: function(item) {
            return isArray(this) ? array_push.apply(this, arguments) : pushShim.apply(this, arguments);
        }
    }, pushIsNotGeneric);
    var pushUndefinedIsWeird = function() {
        var arr = [], result = arr.push(void 0);
        return 1 !== result || 1 !== arr.length || "undefined" != typeof arr[0] || !owns(arr, 0);
    }();
    defineProperties(ArrayPrototype, {
        push: pushShim
    }, pushUndefinedIsWeird), defineProperties(ArrayPrototype, {
        slice: function(start, end) {
            var arr = isString(this) ? strSplit(this, "") : this;
            return arraySliceApply(arr, arguments);
        }
    }, splitString);
    var sortIgnoresNonFunctions = function() {
        try {
            return [ 1, 2 ].sort(null), [ 1, 2 ].sort({}), !0;
        } catch (e) {}
        return !1;
    }(), sortThrowsOnRegex = function() {
        try {
            return [ 1, 2 ].sort(/a/), !1;
        } catch (e) {}
        return !0;
    }(), sortIgnoresUndefined = function() {
        try {
            return [ 1, 2 ].sort(void 0), !0;
        } catch (e) {}
        return !1;
    }();
    defineProperties(ArrayPrototype, {
        sort: function(compareFn) {
            if ("undefined" == typeof compareFn) return arraySort(this);
            if (!isCallable(compareFn)) throw new TypeError("Array.prototype.sort callback must be a function");
            return arraySort(this, compareFn);
        }
    }, sortIgnoresNonFunctions || !sortIgnoresUndefined || !sortThrowsOnRegex);
    var hasDontEnumBug = !isEnum({
        toString: null
    }, "toString"), hasProtoEnumBug = isEnum(function() {}, "prototype"), hasStringEnumBug = !owns("x", "0"), equalsConstructorPrototype = function(o) {
        var ctor = o.constructor;
        return ctor && ctor.prototype === o;
    }, blacklistedKeys = {
        $window: !0,
        $console: !0,
        $parent: !0,
        $self: !0,
        $frame: !0,
        $frames: !0,
        $frameElement: !0,
        $webkitIndexedDB: !0,
        $webkitStorageInfo: !0,
        $external: !0
    }, hasAutomationEqualityBug = function() {
        if ("undefined" == typeof window) return !1;
        for (var k in window) try {
            !blacklistedKeys["$" + k] && owns(window, k) && null !== window[k] && "object" == typeof window[k] && equalsConstructorPrototype(window[k]);
        } catch (e) {
            return !0;
        }
        return !1;
    }(), equalsConstructorPrototypeIfNotBuggy = function(object) {
        if ("undefined" == typeof window || !hasAutomationEqualityBug) return equalsConstructorPrototype(object);
        try {
            return equalsConstructorPrototype(object);
        } catch (e) {
            return !1;
        }
    }, dontEnums = [ "toString", "toLocaleString", "valueOf", "hasOwnProperty", "isPrototypeOf", "propertyIsEnumerable", "constructor" ], dontEnumsLength = dontEnums.length, isStandardArguments = function(value) {
        return "[object Arguments]" === toStr(value);
    }, isLegacyArguments = function(value) {
        return null !== value && "object" == typeof value && "number" == typeof value.length && value.length >= 0 && !isArray(value) && isCallable(value.callee);
    }, isArguments = isStandardArguments(arguments) ? isStandardArguments : isLegacyArguments;
    defineProperties($Object, {
        keys: function(object) {
            var isFn = isCallable(object), isArgs = isArguments(object), isObject = null !== object && "object" == typeof object, isStr = isObject && isString(object);
            if (!isObject && !isFn && !isArgs) throw new TypeError("Object.keys called on a non-object");
            var theKeys = [], skipProto = hasProtoEnumBug && isFn;
            if (isStr && hasStringEnumBug || isArgs) for (var i = 0; i < object.length; ++i) pushCall(theKeys, $String(i));
            if (!isArgs) for (var name in object) skipProto && "prototype" === name || !owns(object, name) || pushCall(theKeys, $String(name));
            if (hasDontEnumBug) for (var skipConstructor = equalsConstructorPrototypeIfNotBuggy(object), j = 0; j < dontEnumsLength; j++) {
                var dontEnum = dontEnums[j];
                skipConstructor && "constructor" === dontEnum || !owns(object, dontEnum) || pushCall(theKeys, dontEnum);
            }
            return theKeys;
        }
    });
    var keysWorksWithArguments = $Object.keys && function() {
        return 2 === $Object.keys(arguments).length;
    }(1, 2), keysHasArgumentsLengthBug = $Object.keys && function() {
        var argKeys = $Object.keys(arguments);
        return 1 !== arguments.length || 1 !== argKeys.length || 1 !== argKeys[0];
    }(1), originalKeys = $Object.keys;
    defineProperties($Object, {
        keys: function(object) {
            return originalKeys(isArguments(object) ? arraySlice(object) : object);
        }
    }, !keysWorksWithArguments || keysHasArgumentsLengthBug);
    var hasToDateStringFormatBug, hasToStringFormatBug, hasNegativeMonthYearBug = 0 !== new Date((-0xc782b5b342b24)).getUTCMonth(), aNegativeTestDate = new Date((-0x55d318d56a724)), aPositiveTestDate = new Date(14496624e5), hasToUTCStringFormatBug = "Mon, 01 Jan -45875 11:59:59 GMT" !== aNegativeTestDate.toUTCString(), timeZoneOffset = aNegativeTestDate.getTimezoneOffset();
    timeZoneOffset < -720 ? (hasToDateStringFormatBug = "Tue Jan 02 -45875" !== aNegativeTestDate.toDateString(), 
    hasToStringFormatBug = !/^Thu Dec 10 2015 \d\d:\d\d:\d\d GMT[-\+]\d\d\d\d(?: |$)/.test(aPositiveTestDate.toString())) : (hasToDateStringFormatBug = "Mon Jan 01 -45875" !== aNegativeTestDate.toDateString(), 
    hasToStringFormatBug = !/^Wed Dec 09 2015 \d\d:\d\d:\d\d GMT[-\+]\d\d\d\d(?: |$)/.test(aPositiveTestDate.toString()));
    var originalGetFullYear = call.bind(Date.prototype.getFullYear), originalGetMonth = call.bind(Date.prototype.getMonth), originalGetDate = call.bind(Date.prototype.getDate), originalGetUTCFullYear = call.bind(Date.prototype.getUTCFullYear), originalGetUTCMonth = call.bind(Date.prototype.getUTCMonth), originalGetUTCDate = call.bind(Date.prototype.getUTCDate), originalGetUTCDay = call.bind(Date.prototype.getUTCDay), originalGetUTCHours = call.bind(Date.prototype.getUTCHours), originalGetUTCMinutes = call.bind(Date.prototype.getUTCMinutes), originalGetUTCSeconds = call.bind(Date.prototype.getUTCSeconds), originalGetUTCMilliseconds = call.bind(Date.prototype.getUTCMilliseconds), dayName = [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ], monthName = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ], daysInMonth = function(month, year) {
        return originalGetDate(new Date(year, month, 0));
    };
    defineProperties(Date.prototype, {
        getFullYear: function() {
            if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
            var year = originalGetFullYear(this);
            return year < 0 && originalGetMonth(this) > 11 ? year + 1 : year;
        },
        getMonth: function() {
            if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
            var year = originalGetFullYear(this), month = originalGetMonth(this);
            return year < 0 && month > 11 ? 0 : month;
        },
        getDate: function() {
            if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
            var year = originalGetFullYear(this), month = originalGetMonth(this), date = originalGetDate(this);
            if (year < 0 && month > 11) {
                if (12 === month) return date;
                var days = daysInMonth(0, year + 1);
                return days - date + 1;
            }
            return date;
        },
        getUTCFullYear: function() {
            if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
            var year = originalGetUTCFullYear(this);
            return year < 0 && originalGetUTCMonth(this) > 11 ? year + 1 : year;
        },
        getUTCMonth: function() {
            if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
            var year = originalGetUTCFullYear(this), month = originalGetUTCMonth(this);
            return year < 0 && month > 11 ? 0 : month;
        },
        getUTCDate: function() {
            if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
            var year = originalGetUTCFullYear(this), month = originalGetUTCMonth(this), date = originalGetUTCDate(this);
            if (year < 0 && month > 11) {
                if (12 === month) return date;
                var days = daysInMonth(0, year + 1);
                return days - date + 1;
            }
            return date;
        }
    }, hasNegativeMonthYearBug), defineProperties(Date.prototype, {
        toUTCString: function() {
            if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
            var day = originalGetUTCDay(this), date = originalGetUTCDate(this), month = originalGetUTCMonth(this), year = originalGetUTCFullYear(this), hour = originalGetUTCHours(this), minute = originalGetUTCMinutes(this), second = originalGetUTCSeconds(this);
            return dayName[day] + ", " + (date < 10 ? "0" + date : date) + " " + monthName[month] + " " + year + " " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + " GMT";
        }
    }, hasNegativeMonthYearBug || hasToUTCStringFormatBug), defineProperties(Date.prototype, {
        toDateString: function() {
            if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
            var day = this.getDay(), date = this.getDate(), month = this.getMonth(), year = this.getFullYear();
            return dayName[day] + " " + monthName[month] + " " + (date < 10 ? "0" + date : date) + " " + year;
        }
    }, hasNegativeMonthYearBug || hasToDateStringFormatBug), (hasNegativeMonthYearBug || hasToStringFormatBug) && (Date.prototype.toString = function() {
        if (!(this && this instanceof Date)) throw new TypeError("this is not a Date object.");
        var day = this.getDay(), date = this.getDate(), month = this.getMonth(), year = this.getFullYear(), hour = this.getHours(), minute = this.getMinutes(), second = this.getSeconds(), timezoneOffset = this.getTimezoneOffset(), hoursOffset = Math.floor(Math.abs(timezoneOffset) / 60), minutesOffset = Math.floor(Math.abs(timezoneOffset) % 60);
        return dayName[day] + " " + monthName[month] + " " + (date < 10 ? "0" + date : date) + " " + year + " " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + " GMT" + (timezoneOffset > 0 ? "-" : "+") + (hoursOffset < 10 ? "0" + hoursOffset : hoursOffset) + (minutesOffset < 10 ? "0" + minutesOffset : minutesOffset);
    }, supportsDescriptors && $Object.defineProperty(Date.prototype, "toString", {
        configurable: !0,
        enumerable: !1,
        writable: !0
    }));
    var negativeDate = -621987552e5, negativeYearString = "-000001", hasNegativeDateBug = Date.prototype.toISOString && new Date(negativeDate).toISOString().indexOf(negativeYearString) === -1, hasSafari51DateBug = Date.prototype.toISOString && "1969-12-31T23:59:59.999Z" !== new Date((-1)).toISOString(), getTime = call.bind(Date.prototype.getTime);
    defineProperties(Date.prototype, {
        toISOString: function() {
            if (!isFinite(this) || !isFinite(getTime(this))) throw new RangeError("Date.prototype.toISOString called on non-finite value.");
            var year = originalGetUTCFullYear(this), month = originalGetUTCMonth(this);
            year += Math.floor(month / 12), month = (month % 12 + 12) % 12;
            var result = [ month + 1, originalGetUTCDate(this), originalGetUTCHours(this), originalGetUTCMinutes(this), originalGetUTCSeconds(this) ];
            year = (year < 0 ? "-" : year > 9999 ? "+" : "") + strSlice("00000" + Math.abs(year), 0 <= year && year <= 9999 ? -4 : -6);
            for (var i = 0; i < result.length; ++i) result[i] = strSlice("00" + result[i], -2);
            return year + "-" + arraySlice(result, 0, 2).join("-") + "T" + arraySlice(result, 2).join(":") + "." + strSlice("000" + originalGetUTCMilliseconds(this), -3) + "Z";
        }
    }, hasNegativeDateBug || hasSafari51DateBug);
    var dateToJSONIsSupported = function() {
        try {
            return Date.prototype.toJSON && null === new Date(NaN).toJSON() && new Date(negativeDate).toJSON().indexOf(negativeYearString) !== -1 && Date.prototype.toJSON.call({
                toISOString: function() {
                    return !0;
                }
            });
        } catch (e) {
            return !1;
        }
    }();
    dateToJSONIsSupported || (Date.prototype.toJSON = function(key) {
        var O = $Object(this), tv = ES.ToPrimitive(O);
        if ("number" == typeof tv && !isFinite(tv)) return null;
        var toISO = O.toISOString;
        if (!isCallable(toISO)) throw new TypeError("toISOString property is not callable");
        return toISO.call(O);
    });
    var supportsExtendedYears = 1e15 === Date.parse("+033658-09-27T01:46:40.000Z"), acceptsInvalidDates = !isNaN(Date.parse("2012-04-04T24:00:00.500Z")) || !isNaN(Date.parse("2012-11-31T23:59:59.000Z")) || !isNaN(Date.parse("2012-12-31T23:59:60.000Z")), doesNotParseY2KNewYear = isNaN(Date.parse("2000-01-01T00:00:00.000Z"));
    if (doesNotParseY2KNewYear || acceptsInvalidDates || !supportsExtendedYears) {
        var maxSafeUnsigned32Bit = Math.pow(2, 31) - 1, hasSafariSignedIntBug = isActualNaN(new Date(1970, 0, 1, 0, 0, 0, maxSafeUnsigned32Bit + 1).getTime());
        Date = function(NativeDate) {
            var DateShim = function(Y, M, D, h, m, s, ms) {
                var date, length = arguments.length;
                if (this instanceof NativeDate) {
                    var seconds = s, millis = ms;
                    if (hasSafariSignedIntBug && length >= 7 && ms > maxSafeUnsigned32Bit) {
                        var msToShift = Math.floor(ms / maxSafeUnsigned32Bit) * maxSafeUnsigned32Bit, sToShift = Math.floor(msToShift / 1e3);
                        seconds += sToShift, millis -= 1e3 * sToShift;
                    }
                    date = 1 === length && $String(Y) === Y ? new NativeDate(DateShim.parse(Y)) : length >= 7 ? new NativeDate(Y, M, D, h, m, seconds, millis) : length >= 6 ? new NativeDate(Y, M, D, h, m, seconds) : length >= 5 ? new NativeDate(Y, M, D, h, m) : length >= 4 ? new NativeDate(Y, M, D, h) : length >= 3 ? new NativeDate(Y, M, D) : length >= 2 ? new NativeDate(Y, M) : length >= 1 ? new NativeDate(Y instanceof NativeDate ? +Y : Y) : new NativeDate();
                } else date = NativeDate.apply(this, arguments);
                return isPrimitive(date) || defineProperties(date, {
                    constructor: DateShim
                }, !0), date;
            }, isoDateExpression = new RegExp("^(\\d{4}|[+-]\\d{6})(?:-(\\d{2})(?:-(\\d{2})(?:T(\\d{2}):(\\d{2})(?::(\\d{2})(?:(\\.\\d{1,}))?)?(Z|(?:([-+])(\\d{2}):(\\d{2})))?)?)?)?$"), months = [ 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 ], dayFromMonth = function(year, month) {
                var t = month > 1 ? 1 : 0;
                return months[month] + Math.floor((year - 1969 + t) / 4) - Math.floor((year - 1901 + t) / 100) + Math.floor((year - 1601 + t) / 400) + 365 * (year - 1970);
            }, toUTC = function(t) {
                var s = 0, ms = t;
                if (hasSafariSignedIntBug && ms > maxSafeUnsigned32Bit) {
                    var msToShift = Math.floor(ms / maxSafeUnsigned32Bit) * maxSafeUnsigned32Bit, sToShift = Math.floor(msToShift / 1e3);
                    s += sToShift, ms -= 1e3 * sToShift;
                }
                return $Number(new NativeDate(1970, 0, 1, 0, 0, s, ms));
            };
            for (var key in NativeDate) owns(NativeDate, key) && (DateShim[key] = NativeDate[key]);
            defineProperties(DateShim, {
                now: NativeDate.now,
                UTC: NativeDate.UTC
            }, !0), DateShim.prototype = NativeDate.prototype, defineProperties(DateShim.prototype, {
                constructor: DateShim
            }, !0);
            var parseShim = function(string) {
                var match = isoDateExpression.exec(string);
                if (match) {
                    var result, year = $Number(match[1]), month = $Number(match[2] || 1) - 1, day = $Number(match[3] || 1) - 1, hour = $Number(match[4] || 0), minute = $Number(match[5] || 0), second = $Number(match[6] || 0), millisecond = Math.floor(1e3 * $Number(match[7] || 0)), isLocalTime = Boolean(match[4] && !match[8]), signOffset = "-" === match[9] ? 1 : -1, hourOffset = $Number(match[10] || 0), minuteOffset = $Number(match[11] || 0), hasMinutesOrSecondsOrMilliseconds = minute > 0 || second > 0 || millisecond > 0;
                    return hour < (hasMinutesOrSecondsOrMilliseconds ? 24 : 25) && minute < 60 && second < 60 && millisecond < 1e3 && month > -1 && month < 12 && hourOffset < 24 && minuteOffset < 60 && day > -1 && day < dayFromMonth(year, month + 1) - dayFromMonth(year, month) && (result = 60 * (24 * (dayFromMonth(year, month) + day) + hour + hourOffset * signOffset), 
                    result = 1e3 * (60 * (result + minute + minuteOffset * signOffset) + second) + millisecond, 
                    isLocalTime && (result = toUTC(result)), -864e13 <= result && result <= 864e13) ? result : NaN;
                }
                return NativeDate.parse.apply(this, arguments);
            };
            return defineProperties(DateShim, {
                parse: parseShim
            }), DateShim;
        }(Date);
    }
    Date.now || (Date.now = function() {
        return new Date().getTime();
    });
    var hasToFixedBugs = NumberPrototype.toFixed && ("0.000" !== 8e-5.toFixed(3) || "1" !== .9.toFixed(0) || "1.25" !== 1.255.toFixed(2) || "1000000000000000128" !== (0xde0b6b3a7640080).toFixed(0)), toFixedHelpers = {
        base: 1e7,
        size: 6,
        data: [ 0, 0, 0, 0, 0, 0 ],
        multiply: function(n, c) {
            for (var i = -1, c2 = c; ++i < toFixedHelpers.size; ) c2 += n * toFixedHelpers.data[i], 
            toFixedHelpers.data[i] = c2 % toFixedHelpers.base, c2 = Math.floor(c2 / toFixedHelpers.base);
        },
        divide: function(n) {
            for (var i = toFixedHelpers.size, c = 0; --i >= 0; ) c += toFixedHelpers.data[i], 
            toFixedHelpers.data[i] = Math.floor(c / n), c = c % n * toFixedHelpers.base;
        },
        numToString: function() {
            for (var i = toFixedHelpers.size, s = ""; --i >= 0; ) if ("" !== s || 0 === i || 0 !== toFixedHelpers.data[i]) {
                var t = $String(toFixedHelpers.data[i]);
                "" === s ? s = t : s += strSlice("0000000", 0, 7 - t.length) + t;
            }
            return s;
        },
        pow: function pow(x, n, acc) {
            return 0 === n ? acc : n % 2 === 1 ? pow(x, n - 1, acc * x) : pow(x * x, n / 2, acc);
        },
        log: function(x) {
            for (var n = 0, x2 = x; x2 >= 4096; ) n += 12, x2 /= 4096;
            for (;x2 >= 2; ) n += 1, x2 /= 2;
            return n;
        }
    }, toFixedShim = function(fractionDigits) {
        var f, x, s, m, e, z, j, k;
        if (f = $Number(fractionDigits), f = isActualNaN(f) ? 0 : Math.floor(f), f < 0 || f > 20) throw new RangeError("Number.toFixed called with invalid number of decimals");
        if (x = $Number(this), isActualNaN(x)) return "NaN";
        if (x <= -1e21 || x >= 1e21) return $String(x);
        if (s = "", x < 0 && (s = "-", x = -x), m = "0", x > 1e-21) if (e = toFixedHelpers.log(x * toFixedHelpers.pow(2, 69, 1)) - 69, 
        z = e < 0 ? x * toFixedHelpers.pow(2, -e, 1) : x / toFixedHelpers.pow(2, e, 1), 
        z *= 4503599627370496, e = 52 - e, e > 0) {
            for (toFixedHelpers.multiply(0, z), j = f; j >= 7; ) toFixedHelpers.multiply(1e7, 0), 
            j -= 7;
            for (toFixedHelpers.multiply(toFixedHelpers.pow(10, j, 1), 0), j = e - 1; j >= 23; ) toFixedHelpers.divide(1 << 23), 
            j -= 23;
            toFixedHelpers.divide(1 << j), toFixedHelpers.multiply(1, 1), toFixedHelpers.divide(2), 
            m = toFixedHelpers.numToString();
        } else toFixedHelpers.multiply(0, z), toFixedHelpers.multiply(1 << -e, 0), m = toFixedHelpers.numToString() + strSlice("0.00000000000000000000", 2, 2 + f);
        return f > 0 ? (k = m.length, m = k <= f ? s + strSlice("0.0000000000000000000", 0, f - k + 2) + m : s + strSlice(m, 0, k - f) + "." + strSlice(m, k - f)) : m = s + m, 
        m;
    };
    defineProperties(NumberPrototype, {
        toFixed: toFixedShim
    }, hasToFixedBugs);
    var hasToPrecisionUndefinedBug = function() {
        try {
            return "1" === 1..toPrecision(void 0);
        } catch (e) {
            return !0;
        }
    }(), originalToPrecision = NumberPrototype.toPrecision;
    defineProperties(NumberPrototype, {
        toPrecision: function(precision) {
            return "undefined" == typeof precision ? originalToPrecision.call(this) : originalToPrecision.call(this, precision);
        }
    }, hasToPrecisionUndefinedBug), 2 !== "ab".split(/(?:ab)*/).length || 4 !== ".".split(/(.?)(.?)/).length || "t" === "tesst".split(/(s)*/)[1] || 4 !== "test".split(/(?:)/, -1).length || "".split(/.?/).length || ".".split(/()()/).length > 1 ? !function() {
        var compliantExecNpcg = "undefined" == typeof /()??/.exec("")[1], maxSafe32BitInt = Math.pow(2, 32) - 1;
        StringPrototype.split = function(separator, limit) {
            var string = String(this);
            if ("undefined" == typeof separator && 0 === limit) return [];
            if (!isRegex(separator)) return strSplit(this, separator, limit);
            var separator2, match, lastIndex, lastLength, output = [], flags = (separator.ignoreCase ? "i" : "") + (separator.multiline ? "m" : "") + (separator.unicode ? "u" : "") + (separator.sticky ? "y" : ""), lastLastIndex = 0, separatorCopy = new RegExp(separator.source, flags + "g");
            compliantExecNpcg || (separator2 = new RegExp("^" + separatorCopy.source + "$(?!\\s)", flags));
            var splitLimit = "undefined" == typeof limit ? maxSafe32BitInt : ES.ToUint32(limit);
            for (match = separatorCopy.exec(string); match && (lastIndex = match.index + match[0].length, 
            !(lastIndex > lastLastIndex && (pushCall(output, strSlice(string, lastLastIndex, match.index)), 
            !compliantExecNpcg && match.length > 1 && match[0].replace(separator2, function() {
                for (var i = 1; i < arguments.length - 2; i++) "undefined" == typeof arguments[i] && (match[i] = void 0);
            }), match.length > 1 && match.index < string.length && array_push.apply(output, arraySlice(match, 1)), 
            lastLength = match[0].length, lastLastIndex = lastIndex, output.length >= splitLimit))); ) separatorCopy.lastIndex === match.index && separatorCopy.lastIndex++, 
            match = separatorCopy.exec(string);
            return lastLastIndex === string.length ? !lastLength && separatorCopy.test("") || pushCall(output, "") : pushCall(output, strSlice(string, lastLastIndex)), 
            output.length > splitLimit ? arraySlice(output, 0, splitLimit) : output;
        };
    }() : "0".split(void 0, 0).length && (StringPrototype.split = function(separator, limit) {
        return "undefined" == typeof separator && 0 === limit ? [] : strSplit(this, separator, limit);
    });
    var str_replace = StringPrototype.replace, replaceReportsGroupsCorrectly = function() {
        var groups = [];
        return "x".replace(/x(.)?/g, function(match, group) {
            pushCall(groups, group);
        }), 1 === groups.length && "undefined" == typeof groups[0];
    }();
    replaceReportsGroupsCorrectly || (StringPrototype.replace = function(searchValue, replaceValue) {
        var isFn = isCallable(replaceValue), hasCapturingGroups = isRegex(searchValue) && /\)[*?]/.test(searchValue.source);
        if (isFn && hasCapturingGroups) {
            var wrappedReplaceValue = function(match) {
                var length = arguments.length, originalLastIndex = searchValue.lastIndex;
                searchValue.lastIndex = 0;
                var args = searchValue.exec(match) || [];
                return searchValue.lastIndex = originalLastIndex, pushCall(args, arguments[length - 2], arguments[length - 1]), 
                replaceValue.apply(this, args);
            };
            return str_replace.call(this, searchValue, wrappedReplaceValue);
        }
        return str_replace.call(this, searchValue, replaceValue);
    });
    var string_substr = StringPrototype.substr, hasNegativeSubstrBug = "".substr && "b" !== "0b".substr(-1);
    defineProperties(StringPrototype, {
        substr: function(start, length) {
            var normalizedStart = start;
            return start < 0 && (normalizedStart = max(this.length + start, 0)), string_substr.call(this, normalizedStart, length);
        }
    }, hasNegativeSubstrBug);
    var ws = "\t\n\x0B\f\r   ᠎             　\u2028\u2029\ufeff", zeroWidth = "​", wsRegexChars = "[" + ws + "]", trimBeginRegexp = new RegExp("^" + wsRegexChars + wsRegexChars + "*"), trimEndRegexp = new RegExp(wsRegexChars + wsRegexChars + "*$"), hasTrimWhitespaceBug = StringPrototype.trim && (ws.trim() || !zeroWidth.trim());
    defineProperties(StringPrototype, {
        trim: function() {
            if ("undefined" == typeof this || null === this) throw new TypeError("can't convert " + this + " to object");
            return $String(this).replace(trimBeginRegexp, "").replace(trimEndRegexp, "");
        }
    }, hasTrimWhitespaceBug);
    var trim = call.bind(String.prototype.trim), hasLastIndexBug = StringPrototype.lastIndexOf && "abcあい".lastIndexOf("あい", 2) !== -1;
    defineProperties(StringPrototype, {
        lastIndexOf: function(searchString) {
            if ("undefined" == typeof this || null === this) throw new TypeError("can't convert " + this + " to object");
            for (var S = $String(this), searchStr = $String(searchString), numPos = arguments.length > 1 ? $Number(arguments[1]) : NaN, pos = isActualNaN(numPos) ? 1 / 0 : ES.ToInteger(numPos), start = min(max(pos, 0), S.length), searchLen = searchStr.length, k = start + searchLen; k > 0; ) {
                k = max(0, k - searchLen);
                var index = strIndexOf(strSlice(S, k, start + searchLen), searchStr);
                if (index !== -1) return k + index;
            }
            return -1;
        }
    }, hasLastIndexBug);
    var originalLastIndexOf = StringPrototype.lastIndexOf;
    if (defineProperties(StringPrototype, {
        lastIndexOf: function(searchString) {
            return originalLastIndexOf.apply(this, arguments);
        }
    }, 1 !== StringPrototype.lastIndexOf.length), 8 === parseInt(ws + "08") && 22 === parseInt(ws + "0x16") || (parseInt = function(origParseInt) {
        var hexRegex = /^[\-+]?0[xX]/;
        return function(str, radix) {
            var string = trim(String(str)), defaultedRadix = $Number(radix) || (hexRegex.test(string) ? 16 : 10);
            return origParseInt(string, defaultedRadix);
        };
    }(parseInt)), 1 / parseFloat("-0") !== -(1 / 0) && (parseFloat = function(origParseFloat) {
        return function(string) {
            var inputString = trim(String(string)), result = origParseFloat(inputString);
            return 0 === result && "-" === strSlice(inputString, 0, 1) ? -0 : result;
        };
    }(parseFloat)), "RangeError: test" !== String(new RangeError("test"))) {
        var errorToStringShim = function() {
            if ("undefined" == typeof this || null === this) throw new TypeError("can't convert " + this + " to object");
            var name = this.name;
            "undefined" == typeof name ? name = "Error" : "string" != typeof name && (name = $String(name));
            var msg = this.message;
            return "undefined" == typeof msg ? msg = "" : "string" != typeof msg && (msg = $String(msg)), 
            name ? msg ? name + ": " + msg : name : msg;
        };
        Error.prototype.toString = errorToStringShim;
    }
    if (supportsDescriptors) {
        var ensureNonEnumerable = function(obj, prop) {
            if (isEnum(obj, prop)) {
                var desc = Object.getOwnPropertyDescriptor(obj, prop);
                desc.configurable && (desc.enumerable = !1, Object.defineProperty(obj, prop, desc));
            }
        };
        ensureNonEnumerable(Error.prototype, "message"), "" !== Error.prototype.message && (Error.prototype.message = ""), 
        ensureNonEnumerable(Error.prototype, "name");
    }
    if ("/a/gim" !== String(/a/gim)) {
        var regexToString = function() {
            var str = "/" + this.source + "/";
            return this.global && (str += "g"), this.ignoreCase && (str += "i"), this.multiline && (str += "m"), 
            str;
        };
        RegExp.prototype.toString = regexToString;
    }
});