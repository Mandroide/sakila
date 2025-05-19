package com.sakila.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeometryUtil {
    public static Point wktToPoint(String wellKnownText)
            throws ParseException {

        return (Point) new WKTReader().read(wellKnownText);
    }
}
