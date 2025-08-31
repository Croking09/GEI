import * as actionTypes from './actionTypes'

export const findProductsCompleted = productSearch => ({
    type: actionTypes.FIND_PRODUCTS_COMPLETED,
    productSearch
});

export const clearProductsSearch = () => ({
    type: actionTypes.CLEAR_PRODUCTS_SEARCH
});

export const insertionCompleted = (productId) => ({
    type: actionTypes.INSERTION_COMPLETED,
    productId
});