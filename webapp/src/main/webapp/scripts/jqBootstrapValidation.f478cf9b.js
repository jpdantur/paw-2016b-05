!function(e) {
    function s(e) {
        return new RegExp("^" + e + "$");
    }
    function o(e, t) {
        for (var n = Array.prototype.slice.call(arguments, 2), r = e.split("."), i = r.pop(), s = 0; s < r.length; s++) t = t[r[s]];
        return t[i].apply(t, n);
    }
    var t = [], n = {
        options: {
            prependExistingHelpBlock: !1,
            sniffHtml: !0,
            preventSubmit: !0,
            submitError: !1,
            submitSuccess: !1,
            semanticallyStrict: !1,
            bindEvents: [],
            autoAdd: {
                helpBlocks: !0
            },
            filter: function() {
                return !0;
            }
        },
        methods: {
            init: function(s) {
                var o = e.extend(!0, {}, n);
                o.options = e.extend(!0, o.options, s);
                var u = this, a = e.unique(u.map(function() {
                    return e(this).parents("form")[0];
                }).toArray());
                return e(a).bind("submit", function(t) {
                    var n = e(this), r = 0, i = n.find("input,textarea,select").not("[type=submit],[type=image]").filter(o.options.filter);
                    i.trigger("submit.validation").trigger("validationLostFocus.validation"), i.each(function(t, n) {
                        var i = e(n), s = i.parents(".control-group").first();
                        (s.hasClass("warning") || s.hasClass("error")) && (s.removeClass("warning").addClass("error"), 
                        r++);
                    }), r ? (o.options.preventSubmit && (t.preventDefault(), t.stopImmediatePropagation()), 
                    n.addClass("error"), e.isFunction(o.options.submitError) && o.options.submitError(n, t, i.jqBootstrapValidation("collectErrors", !0))) : (n.removeClass("error"), 
                    e.isFunction(o.options.submitSuccess) && o.options.submitSuccess(n, t));
                }), this.each(function() {
                    var n = e(this), s = n.parents(".control-group").first(), u = s.find(".help-block").first(), a = n.parents("form").first(), f = [];
                    if (!u.length && o.options.autoAdd && o.options.autoAdd.helpBlocks && (u = e('<div class="help-block" />'), 
                    s.find(".controls").append(u), t.push(u[0])), o.options.sniffHtml) {
                        var l;
                        if (n.data("validationPatternPattern") && n.attr("pattern", n.data("validationPatternPattern")), 
                        void 0 !== n.attr("pattern") && (l = "Not in the expected format\x3c!-- data-validation-pattern-message to override --\x3e", 
                        n.data("validationPatternMessage") && (l = n.data("validationPatternMessage")), 
                        n.data("validationPatternMessage", l), n.data("validationPatternRegex", n.attr("pattern"))), 
                        void 0 !== n.attr("max") || void 0 !== n.attr("aria-valuemax")) {
                            var c = void 0 !== n.attr("max") ? n.attr("max") : n.attr("aria-valuemax");
                            l = "Too high: Maximum of '" + c + "'\x3c!-- data-validation-max-message to override --\x3e", 
                            n.data("validationMaxMessage") && (l = n.data("validationMaxMessage")), n.data("validationMaxMessage", l), 
                            n.data("validationMaxMax", c);
                        }
                        if (void 0 !== n.attr("min") || void 0 !== n.attr("aria-valuemin")) {
                            var h = void 0 !== n.attr("min") ? n.attr("min") : n.attr("aria-valuemin");
                            l = "Too low: Minimum of '" + h + "'\x3c!-- data-validation-min-message to override --\x3e", 
                            n.data("validationMinMessage") && (l = n.data("validationMinMessage")), n.data("validationMinMessage", l), 
                            n.data("validationMinMin", h);
                        }
                        if (void 0 !== n.attr("maxlength") && (l = "Too long: Maximum of '" + n.attr("maxlength") + "' characters\x3c!-- data-validation-maxlength-message to override --\x3e", 
                        n.data("validationMaxlengthMessage") && (l = n.data("validationMaxlengthMessage")), 
                        n.data("validationMaxlengthMessage", l), n.data("validationMaxlengthMaxlength", n.attr("maxlength"))), 
                        void 0 !== n.attr("minlength") && (l = "Too short: Minimum of '" + n.attr("minlength") + "' characters\x3c!-- data-validation-minlength-message to override --\x3e", 
                        n.data("validationMinlengthMessage") && (l = n.data("validationMinlengthMessage")), 
                        n.data("validationMinlengthMessage", l), n.data("validationMinlengthMinlength", n.attr("minlength"))), 
                        void 0 === n.attr("required") && void 0 === n.attr("aria-required") || (l = o.builtInValidators.required.message, 
                        n.data("validationRequiredMessage") && (l = n.data("validationRequiredMessage")), 
                        n.data("validationRequiredMessage", l)), void 0 !== n.attr("type") && "number" === n.attr("type").toLowerCase()) {
                            l = o.validatorTypes.number.message, n.data("validationNumberMessage") && (l = n.data("validationNumberMessage")), 
                            n.data("validationNumberMessage", l);
                            var p = o.validatorTypes.number.step;
                            n.data("validationNumberStep") && (p = n.data("validationNumberStep")), n.data("validationNumberStep", p);
                            var d = o.validatorTypes.number.decimal;
                            n.data("validationNumberDecimal") && (d = n.data("validationNumberDecimal")), n.data("validationNumberDecimal", d);
                        }
                        void 0 !== n.attr("type") && "email" === n.attr("type").toLowerCase() && (l = "Not a valid email address\x3c!-- data-validation-email-message to override --\x3e", 
                        n.data("validationEmailMessage") && (l = n.data("validationEmailMessage")), n.data("validationEmailMessage", l)), 
                        void 0 !== n.attr("minchecked") && (l = "Not enough options checked; Minimum of '" + n.attr("minchecked") + "' required\x3c!-- data-validation-minchecked-message to override --\x3e", 
                        n.data("validationMincheckedMessage") && (l = n.data("validationMincheckedMessage")), 
                        n.data("validationMincheckedMessage", l), n.data("validationMincheckedMinchecked", n.attr("minchecked"))), 
                        void 0 !== n.attr("maxchecked") && (l = "Too many options checked; Maximum of '" + n.attr("maxchecked") + "' required\x3c!-- data-validation-maxchecked-message to override --\x3e", 
                        n.data("validationMaxcheckedMessage") && (l = n.data("validationMaxcheckedMessage")), 
                        n.data("validationMaxcheckedMessage", l), n.data("validationMaxcheckedMaxchecked", n.attr("maxchecked")));
                    }
                    void 0 !== n.data("validation") && (f = n.data("validation").split(",")), e.each(n.data(), function(e, t) {
                        var n = e.replace(/([A-Z])/g, ",$1").split(",");
                        "validation" === n[0] && n[1] && f.push(n[1]);
                    });
                    var v = f, m = [], g = function(e, t) {
                        f[e] = r(t);
                    }, y = function(t, i) {
                        if (void 0 !== n.data("validation" + i + "Shortcut")) e.each(n.data("validation" + i + "Shortcut").split(","), function(e, t) {
                            m.push(t);
                        }); else if (o.builtInValidators[i.toLowerCase()]) {
                            var s = o.builtInValidators[i.toLowerCase()];
                            "shortcut" === s.type.toLowerCase() && e.each(s.shortcut.split(","), function(e, t) {
                                t = r(t), m.push(t), f.push(t);
                            });
                        }
                    };
                    do {
                        e.each(f, g), f = e.unique(f), m = [], e.each(v, y), v = m;
                    } while (v.length > 0);
                    var b = {};
                    e.each(f, function(t, i) {
                        var s = n.data("validation" + i + "Message"), u = !!s, a = !1;
                        if (s || (s = "'" + i + "' validation failed \x3c!-- Add attribute 'data-validation-" + i.toLowerCase() + "-message' to input to change this message --\x3e"), 
                        e.each(o.validatorTypes, function(t, o) {
                            if (void 0 === b[t] && (b[t] = []), !a && void 0 !== n.data("validation" + i + r(o.name))) {
                                var f = o.init(n, i);
                                u && (f.message = s), b[t].push(e.extend(!0, {
                                    name: r(o.name),
                                    message: s
                                }, f)), a = !0;
                            }
                        }), !a && o.builtInValidators[i.toLowerCase()]) {
                            var f = e.extend(!0, {}, o.builtInValidators[i.toLowerCase()]);
                            u && (f.message = s);
                            var l = f.type.toLowerCase();
                            "shortcut" === l ? a = !0 : e.each(o.validatorTypes, function(t, s) {
                                void 0 === b[t] && (b[t] = []), !a && l === t.toLowerCase() && (n.data("validation" + i + r(s.name), f[s.name.toLowerCase()]), 
                                b[l].push(e.extend(f, s.init(n, i))), a = !0);
                            });
                        }
                        a || e.error("Cannot find validation info for '" + i + "'");
                    }), u.data("original-contents", u.data("original-contents") ? u.data("original-contents") : u.html()), 
                    u.data("original-role", u.data("original-role") ? u.data("original-role") : u.attr("role")), 
                    s.data("original-classes", s.data("original-clases") ? s.data("original-classes") : s.attr("class")), 
                    n.data("original-aria-invalid", n.data("original-aria-invalid") ? n.data("original-aria-invalid") : n.attr("aria-invalid")), 
                    n.bind("validation.validation", function(t, r) {
                        var s = i(n), u = [];
                        return e.each(b, function(t, i) {
                            (s || s.length || r && r.includeEmpty || !!o.validatorTypes[t].includeEmpty || !!o.validatorTypes[t].blockSubmit && r && !!r.submitting) && e.each(i, function(e, r) {
                                o.validatorTypes[t].validate(n, s, r) && u.push(r.message);
                            });
                        }), u;
                    }), n.bind("getValidators.validation", function() {
                        return b;
                    }), n.bind("submit.validation", function() {
                        return n.triggerHandler("change.validation", {
                            submitting: !0
                        });
                    }), n.bind((o.options.bindEvents.length > 0 ? o.options.bindEvents : [ "keyup", "focus", "blur", "click", "keydown", "keypress", "change" ]).concat([ "revalidate" ]).join(".validation ") + ".validation", function(t, r) {
                        var f = i(n), l = [];
                        r && r.submitting ? s.data("jqbvIsSubmitting", !0) : "revalidate" !== t.type && s.data("jqbvIsSubmitting", !1);
                        var c = !!s.data("jqbvIsSubmitting");
                        s.find("input,textarea,select").each(function(t, i) {
                            var s = l.length;
                            if (e.each(e(i).triggerHandler("validation.validation", r), function(e, t) {
                                l.push(t);
                            }), l.length > s) e(i).attr("aria-invalid", "true"); else {
                                var o = n.data("original-aria-invalid");
                                e(i).attr("aria-invalid", void 0 !== o && o);
                            }
                        }), a.find("input,select,textarea").not(n).not('[name="' + n.attr("name") + '"]').trigger("validationLostFocus.validation"), 
                        l = e.unique(l.sort()), l.length ? (s.removeClass("success error warning").addClass(c ? "error" : "warning"), 
                        o.options.semanticallyStrict && 1 === l.length ? u.html(l[0] + (o.options.prependExistingHelpBlock ? u.data("original-contents") : "")) : u.html('<ul role="alert"><li>' + l.join("</li><li>") + "</li></ul>" + (o.options.prependExistingHelpBlock ? u.data("original-contents") : ""))) : (s.removeClass("warning error success"), 
                        f.length > 0 && s.addClass("success"), u.html(u.data("original-contents"))), "blur" === t.type && s.removeClass("success");
                    }), n.bind("validationLostFocus.validation", function() {
                        s.removeClass("success");
                    });
                });
            },
            destroy: function() {
                return this.each(function() {
                    var n = e(this), r = n.parents(".control-group").first(), i = r.find(".help-block").first();
                    n.unbind(".validation"), i.html(i.data("original-contents")), r.attr("class", r.data("original-classes")), 
                    n.attr("aria-invalid", n.data("original-aria-invalid")), i.attr("role", n.data("original-role")), 
                    e.inArray(i[0], t) > -1 && i.remove();
                });
            },
            collectErrors: function(t) {
                var n = {};
                return this.each(function(t, r) {
                    var i = e(r), s = i.attr("name"), o = i.triggerHandler("validation.validation", {
                        includeEmpty: !0
                    });
                    n[s] = e.extend(!0, o, n[s]);
                }), e.each(n, function(e, t) {
                    0 === t.length && delete n[e];
                }), n;
            },
            hasErrors: function() {
                var t = [];
                return this.find("input,select,textarea").add(this).each(function(n, r) {
                    t = t.concat(e(r).triggerHandler("getValidators.validation") ? e(r).triggerHandler("validation.validation", {
                        submitting: !0
                    }) : []);
                }), t.length > 0;
            },
            override: function(t) {
                n = e.extend(!0, n, t);
            }
        },
        validatorTypes: {
            callback: {
                name: "callback",
                init: function(e, t) {
                    var n = {
                        validatorName: t,
                        callback: e.data("validation" + t + "Callback"),
                        lastValue: e.val(),
                        lastValid: !0,
                        lastFinished: !0
                    }, r = "Not valid";
                    return e.data("validation" + t + "Message") && (r = e.data("validation" + t + "Message")), 
                    n.message = r, n;
                },
                validate: function(e, t, n) {
                    if (n.lastValue === t && n.lastFinished) return !n.lastValid;
                    if (!0 === n.lastFinished) {
                        n.lastValue = t, n.lastValid = !0, n.lastFinished = !1;
                        var r = n, i = e;
                        o(n.callback, window, e, t, function(t) {
                            r.lastValue === t.value && (r.lastValid = t.valid, t.message && (r.message = t.message), 
                            r.lastFinished = !0, i.data("validation" + r.validatorName + "Message", r.message), 
                            setTimeout(function() {
                                !e.is(":focus") && e.parents("form").first().data("jqbvIsSubmitting") ? i.trigger("blur.validation") : i.trigger("revalidate.validation");
                            }, 1));
                        });
                    }
                    return !1;
                }
            },
            ajax: {
                name: "ajax",
                init: function(e, t) {
                    return {
                        validatorName: t,
                        url: e.data("validation" + t + "Ajax"),
                        lastValue: e.val(),
                        lastValid: !0,
                        lastFinished: !0
                    };
                },
                validate: function(t, n, r) {
                    return "" + r.lastValue == "" + n && !0 === r.lastFinished ? !1 === r.lastValid : (!0 === r.lastFinished && (r.lastValue = n, 
                    r.lastValid = !0, r.lastFinished = !1, e.ajax({
                        url: r.url,
                        data: "value=" + encodeURIComponent(n) + "&field=" + t.attr("name"),
                        dataType: "json",
                        success: function(e) {
                            "" + r.lastValue == "" + e.value && (r.lastValid = !!e.valid, e.message && (r.message = e.message), 
                            r.lastFinished = !0, t.data("validation" + r.validatorName + "Message", r.message), 
                            setTimeout(function() {
                                t.trigger("revalidate.validation");
                            }, 1));
                        },
                        failure: function() {
                            r.lastValid = !0, r.message = "ajax call failed", r.lastFinished = !0, t.data("validation" + r.validatorName + "Message", r.message), 
                            setTimeout(function() {
                                t.trigger("revalidate.validation");
                            }, 1);
                        }
                    })), !1);
                }
            },
            regex: {
                name: "regex",
                init: function(t, n) {
                    var r = {}, i = t.data("validation" + n + "Regex");
                    r.regex = s(i), void 0 === i && e.error("Can't find regex for '" + n + "' validator on '" + t.attr("name") + "'");
                    var o = "Not in the expected format";
                    return t.data("validation" + n + "Message") && (o = t.data("validation" + n + "Message")), 
                    r.message = o, r.originalName = n, r;
                },
                validate: function(e, t, n) {
                    return !n.regex.test(t) && !n.negative || n.regex.test(t) && n.negative;
                }
            },
            email: {
                name: "email",
                init: function(e, t) {
                    var n = {};
                    n.regex = s("[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                    var r = "Not a valid email address";
                    return e.data("validation" + t + "Message") && (r = e.data("validation" + t + "Message")), 
                    n.message = r, n.originalName = t, n;
                },
                validate: function(e, t, n) {
                    return !n.regex.test(t) && !n.negative || n.regex.test(t) && n.negative;
                }
            },
            required: {
                name: "required",
                init: function(e, t) {
                    var n = "This is required";
                    return e.data("validation" + t + "Message") && (n = e.data("validation" + t + "Message")), 
                    {
                        message: n
                    };
                },
                validate: function(e, t, n) {
                    return 0 === t.length && !n.negative || !!(t.length > 0 && n.negative);
                },
                blockSubmit: !0
            },
            match: {
                name: "match",
                init: function(t, n) {
                    var r = t.data("validation" + n + "Match"), i = t.parents("form").first(), s = i.find('[name="' + r + '"]').first();
                    s.bind("validation.validation", function() {
                        t.trigger("revalidate.validation", {
                            submitting: !0
                        });
                    });
                    var o = {};
                    o.element = s, 0 === s.length && e.error("Can't find field '" + r + "' to match '" + t.attr("name") + "' against in '" + n + "' validator");
                    var u = "Must match", a = null;
                    return (a = i.find('label[for="' + r + '"]')).length ? u += " '" + a.text() + "'" : (a = s.parents(".control-group").first().find("label")).length && (u += " '" + a.first().text() + "'"), 
                    t.data("validation" + n + "Message") && (u = t.data("validation" + n + "Message")), 
                    o.message = u, o;
                },
                validate: function(e, t, n) {
                    return t !== n.element.val() && !n.negative || t === n.element.val() && n.negative;
                },
                blockSubmit: !0,
                includeEmpty: !0
            },
            max: {
                name: "max",
                init: function(e, t) {
                    var n = {};
                    return n.max = e.data("validation" + t + "Max"), n.message = "Too high: Maximum of '" + n.max + "'", 
                    e.data("validation" + t + "Message") && (n.message = e.data("validation" + t + "Message")), 
                    n;
                },
                validate: function(e, t, n) {
                    return parseFloat(t, 10) > parseFloat(n.max, 10) && !n.negative || parseFloat(t, 10) <= parseFloat(n.max, 10) && n.negative;
                }
            },
            min: {
                name: "min",
                init: function(e, t) {
                    var n = {};
                    return n.min = e.data("validation" + t + "Min"), n.message = "Too low: Minimum of '" + n.min + "'", 
                    e.data("validation" + t + "Message") && (n.message = e.data("validation" + t + "Message")), 
                    n;
                },
                validate: function(e, t, n) {
                    return parseFloat(t) < parseFloat(n.min) && !n.negative || parseFloat(t) >= parseFloat(n.min) && n.negative;
                }
            },
            maxlength: {
                name: "maxlength",
                init: function(e, t) {
                    var n = {};
                    return n.maxlength = e.data("validation" + t + "Maxlength"), n.message = "Too long: Maximum of '" + n.maxlength + "' characters", 
                    e.data("validation" + t + "Message") && (n.message = e.data("validation" + t + "Message")), 
                    n;
                },
                validate: function(e, t, n) {
                    return t.length > n.maxlength && !n.negative || t.length <= n.maxlength && n.negative;
                }
            },
            minlength: {
                name: "minlength",
                init: function(e, t) {
                    var n = {};
                    return n.minlength = e.data("validation" + t + "Minlength"), n.message = "Too short: Minimum of '" + n.minlength + "' characters", 
                    e.data("validation" + t + "Message") && (n.message = e.data("validation" + t + "Message")), 
                    n;
                },
                validate: function(e, t, n) {
                    return t.length < n.minlength && !n.negative || t.length >= n.minlength && n.negative;
                }
            },
            maxchecked: {
                name: "maxchecked",
                init: function(e, t) {
                    var n = {}, r = e.parents("form").first().find('[name="' + e.attr("name") + '"]');
                    r.bind("change.validation click.validation", function() {
                        e.trigger("revalidate.validation", {
                            includeEmpty: !0
                        });
                    }), n.elements = r, n.maxchecked = e.data("validation" + t + "Maxchecked");
                    var i = "Too many: Max '" + n.maxchecked + "' checked";
                    return e.data("validation" + t + "Message") && (i = e.data("validation" + t + "Message")), 
                    n.message = i, n;
                },
                validate: function(e, t, n) {
                    return n.elements.filter(":checked").length > n.maxchecked && !n.negative || n.elements.filter(":checked").length <= n.maxchecked && n.negative;
                },
                blockSubmit: !0
            },
            minchecked: {
                name: "minchecked",
                init: function(e, t) {
                    var n = {}, r = e.parents("form").first().find('[name="' + e.attr("name") + '"]');
                    r.bind("change.validation click.validation", function() {
                        e.trigger("revalidate.validation", {
                            includeEmpty: !0
                        });
                    }), n.elements = r, n.minchecked = e.data("validation" + t + "Minchecked");
                    var i = "Too few: Min '" + n.minchecked + "' checked";
                    return e.data("validation" + t + "Message") && (i = e.data("validation" + t + "Message")), 
                    n.message = i, n;
                },
                validate: function(e, t, n) {
                    return n.elements.filter(":checked").length < n.minchecked && !n.negative || n.elements.filter(":checked").length >= n.minchecked && n.negative;
                },
                blockSubmit: !0,
                includeEmpty: !0
            },
            number: {
                name: "number",
                init: function(e, t) {
                    var n = {};
                    n.step = 1, e.attr("step") && (n.step = e.attr("step")), e.data("validation" + t + "Step") && (n.step = e.data("validation" + t + "Step")), 
                    n.decimal = ".", e.data("validation" + t + "Decimal") && (n.decimal = e.data("validation" + t + "Decimal")), 
                    n.thousands = "", e.data("validation" + t + "Thousands") && (n.thousands = e.data("validation" + t + "Thousands")), 
                    n.regex = s("([+-]?\\d+(\\" + n.decimal + "\\d+)?)?"), n.message = "Must be a number";
                    var r = e.data("validation" + t + "Message");
                    return r && (n.message = r), n;
                },
                validate: function(e, t, n) {
                    for (var r = t.replace(n.decimal, ".").replace(n.thousands, ""), i = parseFloat(r), s = parseFloat(n.step); s % 1 != 0; ) s *= 10, 
                    i *= 10;
                    var o = n.regex.test(t), u = parseFloat(i) % parseFloat(s) == 0, a = !isNaN(parseFloat(r)) && isFinite(r);
                    return !(o && u && a);
                },
                message: "Must be a number"
            }
        },
        builtInValidators: {
            email: {
                name: "Email",
                type: "email"
            },
            passwordagain: {
                name: "Passwordagain",
                type: "match",
                match: "password",
                message: "Does not match the given password\x3c!-- data-validator-paswordagain-message to override --\x3e"
            },
            positive: {
                name: "Positive",
                type: "shortcut",
                shortcut: "number,positivenumber"
            },
            negative: {
                name: "Negative",
                type: "shortcut",
                shortcut: "number,negativenumber"
            },
            integer: {
                name: "Integer",
                type: "regex",
                regex: "[+-]?\\d+",
                message: "No decimal places allowed\x3c!-- data-validator-integer-message to override --\x3e"
            },
            positivenumber: {
                name: "Positivenumber",
                type: "min",
                min: 0,
                message: "Must be a positive number\x3c!-- data-validator-positivenumber-message to override --\x3e"
            },
            negativenumber: {
                name: "Negativenumber",
                type: "max",
                max: 0,
                message: "Must be a negative number\x3c!-- data-validator-negativenumber-message to override --\x3e"
            },
            required: {
                name: "Required",
                type: "required",
                message: "This is required\x3c!-- data-validator-required-message to override --\x3e"
            },
            checkone: {
                name: "Checkone",
                type: "minchecked",
                minchecked: 1,
                message: "Check at least one option\x3c!-- data-validation-checkone-message to override --\x3e"
            },
            number: {
                name: "Number",
                type: "number",
                decimal: ".",
                step: "1"
            },
            pattern: {
                name: "Pattern",
                type: "regex",
                message: "Not in expected format"
            }
        }
    }, r = function(e) {
        return e.toLowerCase().replace(/(^|\s)([a-z])/g, function(e, t, n) {
            return t + n.toUpperCase();
        });
    }, i = function(t) {
        var n = t.val(), r = t.attr("type"), i = null, s = !!(i = t.parents("form").first()) || !!(i = t.parents(".control-group").first());
        return "checkbox" === r && (n = t.is(":checked") ? n : "", s && (n = i.find("input[type='checkbox'][name='" + t.attr("name") + "']:checked").map(function(t, n) {
            return e(n).val();
        }).toArray().join(","))), "radio" === r && (n = e('input[name="' + t.attr("name") + '"]:checked').length > 0 ? n : "", 
        s && (n = i.find("input[type='radio'][name='" + t.attr("name") + "']:checked").map(function(t, n) {
            return e(n).val();
        }).toArray().join(","))), n;
    };
    e.fn.jqBootstrapValidation = function(t) {
        return n.methods[t] ? n.methods[t].apply(this, Array.prototype.slice.call(arguments, 1)) : "object" != typeof t && t ? (e.error("Method " + t + " does not exist on jQuery.jqBootstrapValidation"), 
        null) : n.methods.init.apply(this, arguments);
    }, e.jqBootstrapValidation = function(t) {
        e(":input").not("[type=image],[type=submit]").jqBootstrapValidation.apply(this, arguments);
    };
}(jQuery);