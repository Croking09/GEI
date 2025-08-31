import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as actions from '../actions';
import * as selectors from '../selectors';
import backend from '../../../backend';
import {Pager} from '../../common';
import Products from './Products';

const FindProdutsResult = () => {

    const productSearch = useSelector(selectors.getProductSearch);
    const dispatch = useDispatch();

    const handleBackNext = async (backClicked, criteria) => {

        dispatch(actions.clearProductsSearch());

        let newCriteria;

        if (backClicked) {
            newCriteria = {page: criteria.page-1}
        } else {
            newCriteria = {page: criteria.page+1}
        }

        const response = await backend.productsService.findUserProducts(newCriteria);

        if (response.ok) {
            dispatch(actions.findProductsCompleted({criteria: newCriteria, result: response.payload}));

        }

    }

    if (!productSearch) {
        return null;
    }

    if (productSearch.result.items.length === 0) {
        return (
            <div className="alert alert-info" role="alert">
                <FormattedMessage id='project.product.FindProdutsResult.noProduts'/>
            </div>
        );
    }

    return (

        <div>
            <Products products={productSearch.result.items}/>
            <Pager
                back={{
                    enabled: productSearch.criteria.page >= 1,
                    onClick: () => handleBackNext(true, productSearch.criteria)}}
                next={{
                    enabled: productSearch.result.existMoreItems,
                    onClick: () => handleBackNext(false, productSearch.criteria)}}/>
        </div>

    );

}

export default FindProdutsResult;
