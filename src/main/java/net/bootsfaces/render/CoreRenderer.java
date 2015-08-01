/**
 *  Copyright 2014-15 by Riccardo Massera (TheCoder4.Eu).
 *  
 *  This file is part of BootsFaces.
 *  
 *  BootsFaces is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BootsFaces is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with BootsFaces. If not, see <http://www.gnu.org/licenses/>.
 */
package net.bootsfaces.render;

import com.sun.org.apache.regexp.internal.RESyntaxException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;




public class CoreRenderer extends Renderer{

    //characters to be escaped in clientId when used in jquery
    private final Pattern charsToEscape = Pattern.compile("([:\\.])");


    protected void renderPassThruAttributes(FacesContext context, UIComponent component, String[] attrs) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        //pre-defined attributes
        if(attrs != null && attrs.length > 0) {
            for(String attribute : attrs) {
                Object value = component.getAttributes().get(attribute);

                if(shouldRenderAttribute(value))
                    writer.writeAttribute(attribute, value.toString(), attribute);
            }
        }
    }

    protected boolean shouldRenderAttribute(Object value) {
        if(value == null)
            return false;

        if(value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        else if(value instanceof Number) {
            Number number = (Number) value;

            if (value instanceof Integer)
                return number.intValue() != Integer.MIN_VALUE;
            else if (value instanceof Double)
                return number.doubleValue() != Double.MIN_VALUE;
            else if (value instanceof Long)
                return number.longValue() != Long.MIN_VALUE;
            else if (value instanceof Byte)
                return number.byteValue() != Byte.MIN_VALUE;
            else if (value instanceof Float)
                return number.floatValue() != Float.MIN_VALUE;
            else if (value instanceof Short)
                return number.shortValue() != Short.MIN_VALUE;
        }

        return true;
    }
	   
    protected void decodeBehaviors(FacesContext context, UIComponent component)  {
        if(!(component instanceof ClientBehaviorHolder)) {
            return;
        }

        Map<String, List<ClientBehavior>> behaviors = ((ClientBehaviorHolder) component).getClientBehaviors();
        if(behaviors.isEmpty()) {
            return;
        }

        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String behaviorEvent = params.get("javax.faces.behavior.event");

        if(null != behaviorEvent) {
            List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);

            if(behaviorsForEvent != null && !behaviorsForEvent.isEmpty()) {
               String behaviorSource = params.get("javax.faces.source");
               String clientId = component.getClientId();

               if(behaviorSource != null && clientId.equals(behaviorSource)) {
                   for(ClientBehavior behavior: behaviorsForEvent) {
                       behavior.decode(context, component);
                   }
               }
            }
        }
    }
    
    public boolean componentIsDisabledOrReadonly(UIComponent component) {
        return Boolean.valueOf(String.valueOf(component.getAttributes().get("disabled"))) 
		|| Boolean.valueOf(String.valueOf(component.getAttributes().get("readonly")));
    }

    protected String escapeClientId(String clientId){
        if (clientId == null){
            return null;
        }
        //prefix all chars in group 1 with double backslash (doubled escaping necessary here)
        return charsToEscape.matcher(clientId).replaceAll("\\\\\\\\$1");
    }





    /**
     * @param rw       ResponseWriter to be used
     * @param name     Attribute name to be added
     * @param value    Attribute value to be added
     * @param property Name of the property or attribute (if any) of the
     *                 {@link UIComponent} associated with the containing element,
     *                 to which this generated attribute corresponds
     * @throws IllegalStateException if this method is called when there
     *                               is no currently open element
     * @throws IOException           if an input/output error occurs
     * @throws NullPointerException  if <code>name</code> is
     *                               <code>null</code>
     */
    protected void writeAttribute(ResponseWriter rw, String name, Object value, String property) throws IOException{
        if ( value == null ) {
            return;
        }
        rw.writeAttribute(name, value, property);
    }

    /**
     * @param rw       ResponseWriter to be used
     * @param text     Text to be written
     * @param property Name of the property or attribute (if any) of the
     *                 {@link UIComponent} associated with the containing element,
     *                 to which this generated text corresponds
     * @throws IOException          if an input/output error occurs
     * @throws NullPointerException if <code>text</code>
     *                              is <code>null</code>
     */
    public void writeText(ResponseWriter rw, Object text, String property) throws IOException {
        if ( text == null || text.equals("")) {
            return;
        }
        rw.writeText(text, property);
    }

    /**
     * @param rw       ResponseWriter to be used
     * @param text      Text to be written
     * @param component The {@link UIComponent} (if any) to which
     *                  this element corresponds
     * @param property  Name of the property or attribute (if any) of the
     *                  {@link UIComponent} associated with the containing element,
     *                  to which this generated text corresponds
     * @throws IOException          if an input/output error occurs
     * @throws NullPointerException if <code>text</code>
     *                              is <code>null</code>
     */
    public void writeText(ResponseWriter rw, Object text, UIComponent component, String property) throws IOException {
        if ( text == null || text.equals("")) {
            return;
        }
        rw.writeText(text, property);
    }


    /**
     * @param rw       ResponseWriter to be used
     * @param text Text to be written
     * @param off  Starting offset (zero-relative)
     * @param len  Number of characters to be written
     * @throws IndexOutOfBoundsException if the calculated starting or
     *                                   ending position is outside the bounds of the character array
     * @throws IOException               if an input/output error occurs
     * @throws NullPointerException      if <code>text</code>
     *                                   is <code>null</code>
     */
    public void writeText(ResponseWriter rw, char text[], int off, int len) throws IOException{
        if ( text == null || text.equals("")) {
            return;
        }
        rw.writeText(text,off,len);
    }

}
