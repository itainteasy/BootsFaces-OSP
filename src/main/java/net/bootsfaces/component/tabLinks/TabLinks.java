/**
 *  Copyright 2014-2016 Riccardo Massera (TheCoder4.Eu)
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

package net.bootsfaces.component.tabLinks;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;

import net.bootsfaces.component.linksContainer.LinksContainer;
import net.bootsfaces.listeners.AddResourcesListener;
import net.bootsfaces.render.Tooltip;
import net.bootsfaces.utils.BsfUtils;

/**
 *
 * @author thecoder4.eu
 */

@FacesComponent("net.bootsfaces.component.tabLinks.TabLinks")
public class TabLinks extends LinksContainer {

	/**
	 * <p>
	 * The standard component type for this component.
	 * </p>
	 */
	public static final String COMPONENT_TYPE = "net.bootsfaces.component.tabLinks.TabLinks";

	public TabLinks() {
		setRendererType(null); // this component renders itself
		AddResourcesListener.addThemedCSSResource("core.css");
		Tooltip.addResourceFiles();
	}

	public void setValueExpression(String name, ValueExpression binding) {
		name = BsfUtils.snakeCaseToCamelCase(name);
		super.setValueExpression(name, binding);
	}

	/*
	 * <ul class="nav nav-tabs"> ... </ul>
	 */
	@Override
	protected String getContainerStyles() {
		return "nav nav-tabs";
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
}
