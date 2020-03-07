/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari;

import enums.EnumDevelopmentPlatform;
import interfaces.Configuration;

/**
 *
 * @author DELL LAB
 */
public class ConfigurationJavaScript extends Configuration{
    public ConfigurationJavaScript(){
        setProgrammingLanguage(EnumDevelopmentPlatform.JAVA_SCRIPT);
    }
}
