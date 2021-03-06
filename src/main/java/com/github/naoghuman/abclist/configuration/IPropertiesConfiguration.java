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
package com.github.naoghuman.abclist.configuration;

import javafx.util.Duration;

/**
 *
 * @author Naoghuman
 */
public interface IPropertiesConfiguration {
    
    public static final int NO_ENTITY = 0;
    
    public static final int TAB_INDEX__LINKS = 2;
    public static final int TAB_INDEX__TERMS = 1;
    public static final int TAB_INDEX__TOPICS = 0;
    
    public static final String STRING_DEFAULT_REGEX = "%s"; // NOI18N
    
    public static final String INFO__FOUNDED_LINKS = "converter.info.founded.links"; // NOI18N
    public static final String INFO__FOUNDED_TERMS = "converter.info.founded.terms"; // NOI18N
    public static final String INFO__FOUNDED_TOPICS = "converter.info.founded.topics"; // NOI18N
    public static final String INFO__FOUNDED_TOPIC_ELEMENTS = "converter.info.founded.topicelements"; // NOI18N
    
    public static final Duration LITTLE_DELAY__DURATION_125 = Duration.millis(125.0d);
    
    public static final String KEY__APPLICATION__RESOURCE_BUNDLE = "/com/github/naoghuman/abclist/i18n/application.properties"; // NOI18N
    public static final String KEY__CONVERTER__RESOURCE_BUNDLE = "/com/github/naoghuman/abclist/i18n/converter.properties"; // NOI18N
    public static final String KEY__TESTDATA__RESOURCE_BUNDLE = "/com/github/naoghuman/abclist/i18n/testdataapplication.properties"; // NOI18N
    
    public static final String KEY__APPLICATION__BORDER_SIGN = "application.border.sign"; // NOI18N
    public static final String KEY__APPLICATION__BUILD_DATETIME = "application.build.datetime"; // NOI18N
    public static final String KEY__APPLICATION__DATABASE = "application.database"; // NOI18N
    public static final String KEY__APPLICATION__MESSAGE_START = "application.message.start"; // NOI18N
    public static final String KEY__APPLICATION__MESSAGE_STOP = "application.message.stop"; // NOI18N
    public static final String KEY__APPLICATION__TITLE = "application.title"; // NOI18N
    public static final String KEY__APPLICATION__VERSION = "application.version"; // NOI18N
    
    public static final String KEY__TESTDATA_APPLICATION__BORDER_SIGN = "application.testdata.border.sign"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__DATABASE = "application.database"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__MESSAGE_START = "application.testdata.message.start"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__MESSAGE_STOP = "application.testdata.message.stop"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__TITLE = "application.testdata.title"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__VERSION = "application.testdata.version"; // NOI18N
    
}
