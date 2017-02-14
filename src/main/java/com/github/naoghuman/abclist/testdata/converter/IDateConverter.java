/*
 * Copyright (C) 2017 Naoghuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.abclist.testdata.converter;

/**
 *
 * @author Naoghuman
 */
public interface IDateConverter {
    
    public static final String PATTERN__DATE = "MM-dd-yyyy"; // NOI18N
    public static final String PATTERN__DATE__COMMENT = "EEEE, MMM d yyyy"; // NOI18N
    public static final String PATTERN__DATE__HISTORY = "EEEE, MMM d yyyy"; // NOI18N
    public static final String PATTERN__DATETIME = "MM-dd-yyyy HH:mm:ss"; // NOI18N
    public static final String PATTERN__GENERATIONTIME = "MM-dd-yyyy  HH:mm:ss"; // NOI18N
    public static final String PATTERN__TIME = "HH:mm:ss"; // NOI18N
    public static final String PATTERN__TIME_IS_EMPTY = "00:00:00"; // NOI18N
    public static final String PATTERN__TIME_WITH_MILLIS = "HH:mm:ss.SSS"; // NOI18N

}