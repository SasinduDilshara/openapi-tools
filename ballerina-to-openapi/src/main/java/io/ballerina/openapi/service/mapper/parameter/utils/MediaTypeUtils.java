package io.ballerina.openapi.service.mapper.parameter.utils;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.api.symbols.TypeSymbol;
import io.ballerina.openapi.service.mapper.parameter.ResponseMapper;

import java.util.List;
import java.util.Objects;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

public final class MediaTypeUtils {

    private static final String JSON_PATTERN = "^(application|text)\\/(.*[.+-]|)json$";
    private static final String XML_PATTERN = "^(application|text)\\/(.*[.+-]|)xml$";
    private static final String TEXT_PATTERN = "^(text)\\/(.*[.+-]|)plain$";
    private static final String OCTET_STREAM_PATTERN = "^(application)\\/(.*[.+-]|)octet-stream$";

    public static String getMediaTypeFromType(TypeSymbol typeSymbol, String prefix, List<String> allowedMediaTypes,
                                              SemanticModel semanticModel) {
        String mediaType;
        if (typeSymbol.subtypeOf(semanticModel.types().STRING)) {
            mediaType = getCompatibleMediaType(allowedMediaTypes, TEXT_PATTERN, TEXT_PLAIN);
        } else if (typeSymbol.subtypeOf(semanticModel.types().XML)) {
            mediaType = getCompatibleMediaType(allowedMediaTypes, XML_PATTERN, APPLICATION_XML);
        } else if (typeSymbol.subtypeOf(
                semanticModel.types().builder().ARRAY_TYPE.withType(semanticModel.types().BYTE).build())) {
            mediaType = getCompatibleMediaType(allowedMediaTypes, OCTET_STREAM_PATTERN, APPLICATION_OCTET_STREAM);
        } else if (ResponseMapper.isSubTypeOfHttpResponse(typeSymbol, semanticModel)) {
            return "*/*";
        } else {
            mediaType = getCompatibleMediaType(allowedMediaTypes, JSON_PATTERN, APPLICATION_JSON);
        }
        return addMediaTypePrefix(mediaType, prefix);
    }

    private static String addMediaTypePrefix(String mediaType, String prefix) {
        if (prefix.trim().isEmpty() || mediaType.equals("*/*")) {
            return mediaType;
        }
        String[] mediaTypeParts = mediaType.split("/");
        if (mediaTypeParts.length == 2) {
            return mediaTypeParts[0] + "/" + prefix + "+" + mediaTypeParts[1];
        }
        return mediaType;
    }

    private static String getCompatibleMediaType(List<String> allowedMediaTypes, String pattern,
                                                 String defaultMediaType) {
        if (Objects.isNull(allowedMediaTypes) || allowedMediaTypes.isEmpty()) {
            return defaultMediaType;
        }
        if (allowedMediaTypes.size() == 1) {
            return allowedMediaTypes.get(0);
        }
        for (String mediaType : allowedMediaTypes) {
            if (mediaType.matches(pattern)) {
                return mediaType;
            }
        }
        return defaultMediaType;
    }

    public static boolean isSameMediaType(TypeSymbol typeSymbol, SemanticModel semanticModel) {
        if (typeSymbol.subtypeOf(semanticModel.types().STRING) || typeSymbol.subtypeOf(semanticModel.types().XML) ||
                typeSymbol.subtypeOf(semanticModel.types().builder().ARRAY_TYPE.withType(
                        semanticModel.types().BYTE).build())) {
            return true;
        }
        return typeSymbol.subtypeOf(semanticModel.types().JSON) && !semanticModel.types().STRING.subtypeOf(typeSymbol);
    }
}
