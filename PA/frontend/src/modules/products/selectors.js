const getModuleState = state => state.products;

export const getProduct = state =>
    getModuleState(state).product;

export const getProductSearch = state =>
    getModuleState(state).productSearch;

export const getLastInsertionId = state =>
    getModuleState(state).lastInsertionId;