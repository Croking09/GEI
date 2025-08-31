
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import * as actions from '../actions';
import backend from '../../../backend';

const FindProducts = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {

        const findProduts = async (criteria) => {
            const response = await backend.productsService.findUserProducts(criteria);

            if (response.ok) {
                dispatch(actions.findProductsCompleted({ criteria, result: response.payload }));
            }
        };

        dispatch(actions.clearProductsSearch());
        navigate('/products/find-products-result');
        findProduts({ page: 0 });
    }, [dispatch, navigate]);

    return null;
};

export default FindProducts;
