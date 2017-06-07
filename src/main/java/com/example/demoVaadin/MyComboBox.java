package com.example.demoVaadin;

import com.sun.deploy.util.StringUtils;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;

import java.util.Collection;
import java.util.function.Function;

/**
 * Created by Michal on 2017-06-07.
 */
public class MyComboBox<T> extends ComboBox {

    private Class<T> tClass;
    private DataProvider<T> dataProvider;
    private Function<String, T> itemConverter;

    public MyComboBox(Class<T> tClass, DataProvider<T> dataProvider, Function<String, T> itemConverter) {
        this.tClass = tClass;
        this.dataProvider = dataProvider;
        this.itemConverter = itemConverter;
        setContainerDataSource(new MyContainer());
        setNewItemsAllowed(true);
        setNewItemHandler(new AbstractSelect.NewItemHandler() {
            @Override
            public void addNewItem(String s) {
                T item = itemConverter.apply(s);
                getContainerDataSource().addItem(item);
                select(item);
            }
        });
        setItemCaptionMode(ItemCaptionMode.PROPERTY);
        setItemCaptionPropertyId("code");
    }

    @Override
    protected Filter buildFilter(String filterString, FilteringMode filteringMode) {
        return new MyFilter(filterString);
    }

    @Override
    protected void setValue(Object newFieldValue, boolean repaintIsNotNeeded) {
        super.setValue(newFieldValue, repaintIsNotNeeded);
    }

    @Override
    public void setValue(Object newValue) throws ReadOnlyException {
        super.setValue(newValue);
    }

    @FunctionalInterface
    interface DataProvider<TYPE> {
        Collection<TYPE> getItems(String filter);
    }

    class MyContainer extends BeanItemContainer<T> {

        public MyContainer() throws IllegalArgumentException {
            super(tClass);
        }

        @Override
        protected void addFilter(Filter filter) throws UnsupportedFilterException {
            String filter1 = ((MyFilter) filter).getFilter();
            if (filter1 != null && !"".equals(filter1)) {
                removeAllItems();
                Collection<T> items = dataProvider.getItems(filter1);
                addItems(items);
            }
        }
    }

    class MyFilter implements Filter {

        private String filter;

        MyFilter(String filter) {
            this.filter = filter;
        }

        public String getFilter() {
            return filter;
        }

        @Override
        public boolean passesFilter(Object o, Item item) throws UnsupportedOperationException {
            return false;
        }

        @Override
        public boolean appliesToProperty(Object o) {
            return false;
        }
    }

}
