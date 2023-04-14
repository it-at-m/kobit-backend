package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.view.LinkView;
import de.muenchen.kobit.backend.validation.exception.InvalidLinkException;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class ValidateLinks implements Validator {

    private static final int LINK_NAME_MAX_SIZE = 100;
    private static final int LINK_MAX_SIZE = 2000;

    @Override
    public void validate(ContactPointView contactPointView) throws InvalidLinkException {
        for (LinkView link : contactPointView.getLinks()) {
            if (anyFieldsNull(link)) {
                throw new InvalidLinkException("Link fields name and url can not be null!");
            }
            if (!isURLValid(link.getUrl())) {
                throw new InvalidLinkException("Link url is not valid!");
            }
            if (isLinkNameTooLarge(link.getName())) {
                throw new InvalidLinkException("Link name must be not more than 100 characters!");
            }
            if (isLinkTooLarge(link.getUrl())) {
                throw new InvalidLinkException("Link must be not more than 2000 characters!");
            }
        }
    }

    private boolean anyFieldsNull(LinkView view) {
        return view.getName() == null || view.getUrl() == null;
    }

    private boolean isURLValid(String url) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }

    private boolean isLinkNameTooLarge(String description) {
        return description.length() > LINK_NAME_MAX_SIZE;
    }

    private boolean isLinkTooLarge(String description) {
        return description.length() > LINK_MAX_SIZE;
    }
}
