import {useSelector} from "react-redux";

import * as selectors from '../../catalog/selectors.js';
import {FormattedMessage} from "react-intl";

const CategorySelector = ({displayText, ...selectProps}) => {
    const categories = useSelector(selectors.getCategories);

    return (
        <select {...selectProps}>
            <FormattedMessage id={displayText ? displayText : 'project.global.fields.category'}>
                {message => (<option value="">{message}</option>)}
            </FormattedMessage>

            {categories && categories.map(category =>
                <option key={category.id} value={category.id}>{category.nombre}</option>
            )}
        </select>
    );
}

export default CategorySelector;