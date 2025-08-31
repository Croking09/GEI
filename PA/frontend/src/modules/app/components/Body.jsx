import {useSelector} from 'react-redux';
import {Route, Routes} from 'react-router-dom';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout} from '../../users';
import users from '../../users';
import {FindProductsResult, ProductDetails} from "../../catalog/index.js";
import FindBids from "../../bid/components/FindBids.jsx";
import FindBidsResult from "../../bid/components/FindBidsResult.jsx";

import FindUserProducts from "../../products/components/FindUserProducts.jsx";
import FindUserProductsResult from "../../products/components/FindUserProductsResult.jsx";
import InsertProductForm from "../../products/components/InsertProductForm.jsx";
import InsertionCompleted from "../../products/components/InsertionCompleted.jsx";


const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    
   return (

        <div className="container">
            <br/>
            <AppGlobalComponents/>
            <Routes>
                <Route path="/*" element={<Home/>}/>
                <Route path="/catalog/find-products-result" element={<FindProductsResult/>}/>
                <Route path="/catalog/product-details/:id" element={<ProductDetails/>}/>
                {loggedIn && <Route path="/users/update-profile" element={<UpdateProfile/>}/>}
                {loggedIn && <Route path="/users/change-password" element={<ChangePassword/>}/>}
                {loggedIn && <Route path="/bid/find-bids" element={<FindBids/>}/>}
                {loggedIn && <Route path="/bid/find-bids-result" element={<FindBidsResult/>}/>}
                {loggedIn && <Route path="/products/find-products" element={<FindUserProducts/>}/>}
                {loggedIn && <Route path="/products/find-products-result" element={<FindUserProductsResult/>}/>}
                {loggedIn && <Route path="/products/add" element={<InsertProductForm/>}/>}
                {loggedIn && <Route path="/products/insertion-completed" element={<InsertionCompleted/>}/>}
                {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
                {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
                {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}
            </Routes>
        </div>

    );

};

export default Body;
