/**
 * 
 */
package org.dhamma.users.client.activities;

import de.mkristian.gwt.rails.session.SessionHandler;
import de.mkristian.gwt.rails.session.SessionManager;

public abstract class AbstractCache<T> {

    protected AbstractCache(SessionManager<T> session){
        session.addSessionHandler(new SessionHandler<T>() {

            public void accessDenied() {
            }

            public void login(T user) {
            }

            public void logout() {
                purgeCache();
            }

            public void timeout() {
                purgeCache();
            }
        });
    }
    
    protected abstract void purgeCache();
}