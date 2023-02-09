package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.view.LinkView;
import de.muenchen.kobit.backend.validation.exception.InvalidLinkException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class ValidateLinks implements Validator {

    @Override
    public void validate(ContactPointView contactPointView) throws InvalidLinkException {
        for (LinkView link : contactPointView.getLinks()) {
            if (anyFieldsNull(link)) {
                throw new InvalidLinkException("Link fields name and url can not be null!");
            }
            if (!isURLValid(link.getUrl())) {
                throw new InvalidLinkException("Link url is not valid!");
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
}
