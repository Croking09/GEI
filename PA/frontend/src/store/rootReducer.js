import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import catalog from '../modules/catalog'
import bid from '../modules/bid'
import products from '../modules/products'

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    catalog: catalog.reducer,
    bid: bid.reducer,
    products: products.reducer
});

export default rootReducer;
