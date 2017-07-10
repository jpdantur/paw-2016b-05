!function(factory) {
    if ("object" == typeof exports) module.exports = factory(); else if ("function" == typeof define && define.amd) define(factory); else {
        var glob;
        try {
            glob = window;
        } catch (e) {
            glob = self;
        }
        glob.SparkMD5 = factory();
    }
}(function(undefined) {
    return function(arrayBuffer) {
        for (var a, b, c, d, chunk, base64 = "", encodings = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", bytes = new Uint8Array(arrayBuffer), byteLength = bytes.byteLength, byteRemainder = byteLength % 3, mainLength = byteLength - byteRemainder, i = 0; i < mainLength; i += 3) chunk = bytes[i] << 16 | bytes[i + 1] << 8 | bytes[i + 2], 
        a = (16515072 & chunk) >> 18, b = (258048 & chunk) >> 12, c = (4032 & chunk) >> 6, 
        d = 63 & chunk, base64 += encodings[a] + encodings[b] + encodings[c] + encodings[d];
        return 1 === byteRemainder ? (chunk = bytes[mainLength], a = (252 & chunk) >> 2, 
        b = (3 & chunk) << 4, base64 += encodings[a] + encodings[b] + "==") : 2 === byteRemainder && (chunk = bytes[mainLength] << 8 | bytes[mainLength + 1], 
        a = (64512 & chunk) >> 10, b = (1008 & chunk) >> 4, c = (15 & chunk) << 2, base64 += encodings[a] + encodings[b] + encodings[c] + "="), 
        base64;
    };
});