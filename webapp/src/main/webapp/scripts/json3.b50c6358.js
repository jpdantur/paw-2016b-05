(function() {
    function runInContext(context, exports) {
        function has(name) {
            if (has[name] !== undef) return has[name];
            var isSupported;
            if ("bug-string-char-index" == name) isSupported = "a" != "a"[0]; else if ("json" == name) isSupported = has("json-stringify") && has("json-parse"); else {
                var value, serialized = '{"a":[1,true,false,null,"\\u0000\\b\\n\\f\\r\\t"]}';
                if ("json-stringify" == name) {
                    var stringify = exports.stringify, stringifySupported = "function" == typeof stringify && isExtended;
                    if (stringifySupported) {
                        (value = function() {
                            return 1;
                        }).toJSON = value;
                        try {
                            stringifySupported = "0" === stringify(0) && "0" === stringify(new Number()) && '""' == stringify(new String()) && stringify(getClass) === undef && stringify(undef) === undef && stringify() === undef && "1" === stringify(value) && "[1]" == stringify([ value ]) && "[null]" == stringify([ undef ]) && "null" == stringify(null) && "[null,null,null]" == stringify([ undef, getClass, null ]) && stringify({
                                a: [ value, !0, !1, null, "\0\b\n\f\r\t" ]
                            }) == serialized && "1" === stringify(null, value) && "[\n 1,\n 2\n]" == stringify([ 1, 2 ], null, 1) && '"-271821-04-20T00:00:00.000Z"' == stringify(new Date(-864e13)) && '"+275760-09-13T00:00:00.000Z"' == stringify(new Date(864e13)) && '"-000001-01-01T00:00:00.000Z"' == stringify(new Date(-621987552e5)) && '"1969-12-31T23:59:59.999Z"' == stringify(new Date(-1));
                        } catch (exception) {
                            stringifySupported = !1;
                        }
                    }
                    isSupported = stringifySupported;
                }
                if ("json-parse" == name) {
                    var parse = exports.parse;
                    if ("function" == typeof parse) try {
                        if (0 === parse("0") && !parse(!1)) {
                            value = parse(serialized);
                            var parseSupported = 5 == value.a.length && 1 === value.a[0];
                            if (parseSupported) {
                                try {
                                    parseSupported = !parse('"\t"');
                                } catch (exception) {}
                                if (parseSupported) try {
                                    parseSupported = 1 !== parse("01");
                                } catch (exception) {}
                                if (parseSupported) try {
                                    parseSupported = 1 !== parse("1.");
                                } catch (exception) {}
                            }
                        }
                    } catch (exception) {
                        parseSupported = !1;
                    }
                    isSupported = parseSupported;
                }
            }
            return has[name] = !!isSupported;
        }
        context || (context = root.Object()), exports || (exports = root.Object());
        var Number = context.Number || root.Number, String = context.String || root.String, Object = context.Object || root.Object, Date = context.Date || root.Date, SyntaxError = context.SyntaxError || root.SyntaxError, TypeError = context.TypeError || root.TypeError, Math = context.Math || root.Math, nativeJSON = context.JSON || root.JSON;
        "object" == typeof nativeJSON && nativeJSON && (exports.stringify = nativeJSON.stringify, 
        exports.parse = nativeJSON.parse);
        var isProperty, forEach, undef, objectProto = Object.prototype, getClass = objectProto.toString, isExtended = new Date(-0xc782b5b800cec);
        try {
            isExtended = -109252 == isExtended.getUTCFullYear() && 0 === isExtended.getUTCMonth() && 1 === isExtended.getUTCDate() && 10 == isExtended.getUTCHours() && 37 == isExtended.getUTCMinutes() && 6 == isExtended.getUTCSeconds() && 708 == isExtended.getUTCMilliseconds();
        } catch (exception) {}
        if (!has("json")) {
            var charIndexBuggy = has("bug-string-char-index");
            if (!isExtended) var floor = Math.floor, Months = [ 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 ], getDay = function(year, month) {
                return Months[month] + 365 * (year - 1970) + floor((year - 1969 + (month = +(month > 1))) / 4) - floor((year - 1901 + month) / 100) + floor((year - 1601 + month) / 400);
            };
            if ((isProperty = objectProto.hasOwnProperty) || (isProperty = function(property) {
                var constructor, members = {};
                return (members.__proto__ = null, members.__proto__ = {
                    toString: 1
                }, members).toString != getClass ? isProperty = function(property) {
                    var original = this.__proto__, result = property in (this.__proto__ = null, this);
                    return this.__proto__ = original, result;
                } : (constructor = members.constructor, isProperty = function(property) {
                    var parent = (this.constructor || constructor).prototype;
                    return property in this && !(property in parent && this[property] === parent[property]);
                }), members = null, isProperty.call(this, property);
            }), forEach = function(object, callback) {
                var Properties, members, property, size = 0;
                (Properties = function() {
                    this.valueOf = 0;
                }).prototype.valueOf = 0, members = new Properties();
                for (property in members) isProperty.call(members, property) && size++;
                return Properties = members = null, size ? forEach = 2 == size ? function(object, callback) {
                    var property, members = {}, isFunction = "[object Function]" == getClass.call(object);
                    for (property in object) isFunction && "prototype" == property || isProperty.call(members, property) || !(members[property] = 1) || !isProperty.call(object, property) || callback(property);
                } : function(object, callback) {
                    var property, isConstructor, isFunction = "[object Function]" == getClass.call(object);
                    for (property in object) isFunction && "prototype" == property || !isProperty.call(object, property) || (isConstructor = "constructor" === property) || callback(property);
                    (isConstructor || isProperty.call(object, property = "constructor")) && callback(property);
                } : (members = [ "valueOf", "toString", "toLocaleString", "propertyIsEnumerable", "isPrototypeOf", "hasOwnProperty", "constructor" ], 
                forEach = function(object, callback) {
                    var property, length, isFunction = "[object Function]" == getClass.call(object), hasProperty = !isFunction && "function" != typeof object.constructor && objectTypes[typeof object.hasOwnProperty] && object.hasOwnProperty || isProperty;
                    for (property in object) isFunction && "prototype" == property || !hasProperty.call(object, property) || callback(property);
                    for (length = members.length; property = members[--length]; hasProperty.call(object, property) && callback(property)) ;
                }), forEach(object, callback);
            }, !has("json-stringify")) {
                var Escapes = {
                    92: "\\\\",
                    34: '\\"',
                    8: "\\b",
                    12: "\\f",
                    10: "\\n",
                    13: "\\r",
                    9: "\\t"
                }, toPaddedString = function(width, value) {
                    return ("000000" + (value || 0)).slice(-width);
                }, quote = function(value) {
                    for (var result = '"', index = 0, length = value.length, useCharIndex = !charIndexBuggy || length > 10, symbols = useCharIndex && (charIndexBuggy ? value.split("") : value); index < length; index++) {
                        var charCode = value.charCodeAt(index);
                        switch (charCode) {
                          case 8:
                          case 9:
                          case 10:
                          case 12:
                          case 13:
                          case 34:
                          case 92:
                            result += Escapes[charCode];
                            break;

                          default:
                            if (charCode < 32) {
                                result += "\\u00" + toPaddedString(2, charCode.toString(16));
                                break;
                            }
                            result += useCharIndex ? symbols[index] : value.charAt(index);
                        }
                    }
                    return result + '"';
                }, serialize = function(property, object, callback, properties, whitespace, indentation, stack) {
                    var value, className, year, month, date, time, hours, minutes, seconds, milliseconds, results, element, index, length, prefix, result;
                    try {
                        value = object[property];
                    } catch (exception) {}
                    if ("object" == typeof value && value) if ("[object Date]" != (className = getClass.call(value)) || isProperty.call(value, "toJSON")) "function" == typeof value.toJSON && ("[object Number]" != className && "[object String]" != className && "[object Array]" != className || isProperty.call(value, "toJSON")) && (value = value.toJSON(property)); else if (value > -1 / 0 && value < 1 / 0) {
                        if (getDay) {
                            for (date = floor(value / 864e5), year = floor(date / 365.2425) + 1970 - 1; getDay(year + 1, 0) <= date; year++) ;
                            for (month = floor((date - getDay(year, 0)) / 30.42); getDay(year, month + 1) <= date; month++) ;
                            date = 1 + date - getDay(year, month), time = (value % 864e5 + 864e5) % 864e5, hours = floor(time / 36e5) % 24, 
                            minutes = floor(time / 6e4) % 60, seconds = floor(time / 1e3) % 60, milliseconds = time % 1e3;
                        } else year = value.getUTCFullYear(), month = value.getUTCMonth(), date = value.getUTCDate(), 
                        hours = value.getUTCHours(), minutes = value.getUTCMinutes(), seconds = value.getUTCSeconds(), 
                        milliseconds = value.getUTCMilliseconds();
                        value = (year <= 0 || year >= 1e4 ? (year < 0 ? "-" : "+") + toPaddedString(6, year < 0 ? -year : year) : toPaddedString(4, year)) + "-" + toPaddedString(2, month + 1) + "-" + toPaddedString(2, date) + "T" + toPaddedString(2, hours) + ":" + toPaddedString(2, minutes) + ":" + toPaddedString(2, seconds) + "." + toPaddedString(3, milliseconds) + "Z";
                    } else value = null;
                    if (callback && (value = callback.call(object, property, value)), null === value) return "null";
                    if ("[object Boolean]" == (className = getClass.call(value))) return "" + value;
                    if ("[object Number]" == className) return value > -1 / 0 && value < 1 / 0 ? "" + value : "null";
                    if ("[object String]" == className) return quote("" + value);
                    if ("object" == typeof value) {
                        for (length = stack.length; length--; ) if (stack[length] === value) throw TypeError();
                        if (stack.push(value), results = [], prefix = indentation, indentation += whitespace, 
                        "[object Array]" == className) {
                            for (index = 0, length = value.length; index < length; index++) element = serialize(index, value, callback, properties, whitespace, indentation, stack), 
                            results.push(element === undef ? "null" : element);
                            result = results.length ? whitespace ? "[\n" + indentation + results.join(",\n" + indentation) + "\n" + prefix + "]" : "[" + results.join(",") + "]" : "[]";
                        } else forEach(properties || value, function(property) {
                            var element = serialize(property, value, callback, properties, whitespace, indentation, stack);
                            element !== undef && results.push(quote(property) + ":" + (whitespace ? " " : "") + element);
                        }), result = results.length ? whitespace ? "{\n" + indentation + results.join(",\n" + indentation) + "\n" + prefix + "}" : "{" + results.join(",") + "}" : "{}";
                        return stack.pop(), result;
                    }
                };
                exports.stringify = function(source, filter, width) {
                    var whitespace, callback, properties, className;
                    if (objectTypes[typeof filter] && filter) if ("[object Function]" == (className = getClass.call(filter))) callback = filter; else if ("[object Array]" == className) {
                        properties = {};
                        for (var value, index = 0, length = filter.length; index < length; value = filter[index++], 
                        ("[object String]" == (className = getClass.call(value)) || "[object Number]" == className) && (properties[value] = 1)) ;
                    }
                    if (width) if ("[object Number]" == (className = getClass.call(width))) {
                        if ((width -= width % 1) > 0) for (whitespace = "", width > 10 && (width = 10); whitespace.length < width; whitespace += " ") ;
                    } else "[object String]" == className && (whitespace = width.length <= 10 ? width : width.slice(0, 10));
                    return serialize("", (value = {}, value[""] = source, value), callback, properties, whitespace, "", []);
                };
            }
            if (!has("json-parse")) {
                var Index, Source, fromCharCode = String.fromCharCode, Unescapes = {
                    92: "\\",
                    34: '"',
                    47: "/",
                    98: "\b",
                    116: "\t",
                    110: "\n",
                    102: "\f",
                    114: "\r"
                }, abort = function() {
                    throw Index = Source = null, SyntaxError();
                }, lex = function() {
                    for (var value, begin, position, isSigned, charCode, source = Source, length = source.length; Index < length; ) switch (charCode = source.charCodeAt(Index)) {
                      case 9:
                      case 10:
                      case 13:
                      case 32:
                        Index++;
                        break;

                      case 123:
                      case 125:
                      case 91:
                      case 93:
                      case 58:
                      case 44:
                        return value = charIndexBuggy ? source.charAt(Index) : source[Index], Index++, value;

                      case 34:
                        for (value = "@", Index++; Index < length; ) if ((charCode = source.charCodeAt(Index)) < 32) abort(); else if (92 == charCode) switch (charCode = source.charCodeAt(++Index)) {
                          case 92:
                          case 34:
                          case 47:
                          case 98:
                          case 116:
                          case 110:
                          case 102:
                          case 114:
                            value += Unescapes[charCode], Index++;
                            break;

                          case 117:
                            for (begin = ++Index, position = Index + 4; Index < position; Index++) (charCode = source.charCodeAt(Index)) >= 48 && charCode <= 57 || charCode >= 97 && charCode <= 102 || charCode >= 65 && charCode <= 70 || abort();
                            value += fromCharCode("0x" + source.slice(begin, Index));
                            break;

                          default:
                            abort();
                        } else {
                            if (34 == charCode) break;
                            for (charCode = source.charCodeAt(Index), begin = Index; charCode >= 32 && 92 != charCode && 34 != charCode; ) charCode = source.charCodeAt(++Index);
                            value += source.slice(begin, Index);
                        }
                        if (34 == source.charCodeAt(Index)) return Index++, value;
                        abort();

                      default:
                        if (begin = Index, 45 == charCode && (isSigned = !0, charCode = source.charCodeAt(++Index)), 
                        charCode >= 48 && charCode <= 57) {
                            for (48 == charCode && (charCode = source.charCodeAt(Index + 1)) >= 48 && charCode <= 57 && abort(), 
                            isSigned = !1; Index < length && (charCode = source.charCodeAt(Index)) >= 48 && charCode <= 57; Index++) ;
                            if (46 == source.charCodeAt(Index)) {
                                for (position = ++Index; position < length && (charCode = source.charCodeAt(position)) >= 48 && charCode <= 57; position++) ;
                                position == Index && abort(), Index = position;
                            }
                            if (101 == (charCode = source.charCodeAt(Index)) || 69 == charCode) {
                                for (charCode = source.charCodeAt(++Index), 43 != charCode && 45 != charCode || Index++, 
                                position = Index; position < length && (charCode = source.charCodeAt(position)) >= 48 && charCode <= 57; position++) ;
                                position == Index && abort(), Index = position;
                            }
                            return +source.slice(begin, Index);
                        }
                        if (isSigned && abort(), "true" == source.slice(Index, Index + 4)) return Index += 4, 
                        !0;
                        if ("false" == source.slice(Index, Index + 5)) return Index += 5, !1;
                        if ("null" == source.slice(Index, Index + 4)) return Index += 4, null;
                        abort();
                    }
                    return "$";
                }, get = function(value) {
                    var results, hasMembers;
                    if ("$" == value && abort(), "string" == typeof value) {
                        if ("@" == (charIndexBuggy ? value.charAt(0) : value[0])) return value.slice(1);
                        if ("[" == value) {
                            for (results = []; "]" != (value = lex()); hasMembers || (hasMembers = !0)) hasMembers && ("," == value ? "]" == (value = lex()) && abort() : abort()), 
                            "," == value && abort(), results.push(get(value));
                            return results;
                        }
                        if ("{" == value) {
                            for (results = {}; "}" != (value = lex()); hasMembers || (hasMembers = !0)) hasMembers && ("," == value ? "}" == (value = lex()) && abort() : abort()), 
                            "," != value && "string" == typeof value && "@" == (charIndexBuggy ? value.charAt(0) : value[0]) && ":" == lex() || abort(), 
                            results[value.slice(1)] = get(lex());
                            return results;
                        }
                        abort();
                    }
                    return value;
                }, update = function(source, property, callback) {
                    var element = walk(source, property, callback);
                    element === undef ? delete source[property] : source[property] = element;
                }, walk = function(source, property, callback) {
                    var length, value = source[property];
                    if ("object" == typeof value && value) if ("[object Array]" == getClass.call(value)) for (length = value.length; length--; ) update(value, length, callback); else forEach(value, function(property) {
                        update(value, property, callback);
                    });
                    return callback.call(source, property, value);
                };
                exports.parse = function(source, callback) {
                    var result, value;
                    return Index = 0, Source = "" + source, result = get(lex()), "$" != lex() && abort(), 
                    Index = Source = null, callback && "[object Function]" == getClass.call(callback) ? walk((value = {}, 
                    value[""] = result, value), "", callback) : result;
                };
            }
        }
        return exports.runInContext = runInContext, exports;
    }
    var isLoader = "function" == typeof define && define.amd, objectTypes = {
        function: !0,
        object: !0
    }, freeExports = objectTypes[typeof exports] && exports && !exports.nodeType && exports, root = objectTypes[typeof window] && window || this, freeGlobal = freeExports && objectTypes[typeof module] && module && !module.nodeType && "object" == typeof global && global;
    if (!freeGlobal || freeGlobal.global !== freeGlobal && freeGlobal.window !== freeGlobal && freeGlobal.self !== freeGlobal || (root = freeGlobal), 
    freeExports && !isLoader) runInContext(root, freeExports); else {
        var nativeJSON = root.JSON, previousJSON = root.JSON3, isRestored = !1, JSON3 = runInContext(root, root.JSON3 = {
            noConflict: function() {
                return isRestored || (isRestored = !0, root.JSON = nativeJSON, root.JSON3 = previousJSON, 
                nativeJSON = previousJSON = null), JSON3;
            }
        });
        root.JSON = {
            parse: JSON3.parse,
            stringify: JSON3.stringify
        };
    }
    isLoader && define([], function() {
        return JSON3;
    });
}).call(this);