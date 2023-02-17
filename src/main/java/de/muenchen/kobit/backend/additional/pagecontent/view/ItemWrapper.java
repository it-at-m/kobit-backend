package de.muenchen.kobit.backend.additional.pagecontent.view;

import java.util.List;

public class ItemWrapper {

    private final List<TextItemView> textItemView;
    private final List<ContentItemView> contentItemView;

    public List<TextItemView> getTextItemView() {
        return textItemView;
    }

    public List<ContentItemView> getContentItemView() {
        return contentItemView;
    }

    private ItemWrapper(ItemWrapperBuilder builder) {
        this.textItemView = builder.textItemView;
        this.contentItemView = builder.contentItemView;
    }

    public static class ItemWrapperBuilder {
        private List<TextItemView> textItemView;
        private List<ContentItemView> contentItemView;

        public ItemWrapperBuilder() {}

        public ItemWrapperBuilder textItems(List<TextItemView> textItemView) {
            this.textItemView = textItemView;
            return this;
        }

        public ItemWrapperBuilder contentItems(List<ContentItemView> contentItemView) {
            this.contentItemView = contentItemView;
            return this;
        }

        public ItemWrapper build() {
            return new ItemWrapper(this);
        }
    }
}
