import {FormattedMessage} from "react-intl";
import {useSelector} from "react-redux";
import * as selectors from "../selectors.js";
import {ProductLink} from "../../common/index.js";

const InsertionCompleted = () => {

    const productId = useSelector(selectors.getLastInsertionId);

    if (!productId) {
        return null;
    }

    return (
        <div id="insertionMsg" className="alert alert-success" role="alert">
            <FormattedMessage id="project.products.insertionCompleted"/>.
            &nbsp;
            <ProductLink id={productId} name={<FormattedMessage id="project.global.fields.details"/>}/>
        </div>
    );
};

export default InsertionCompleted;