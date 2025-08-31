import {FormattedMessage, FormattedNumber} from "react-intl";

import * as selectors from '../selectors'
import {ProductLink} from '../../common'

const Products =({products, categories}) => (
    <table className="table table-striped table-hover">

        <thead>
        <tr>
            <th scope="col">
                <FormattedMessage id='project.global.fields.category'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.name'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.currentPrice'/>
            </th>
            <th scope="col">
                <FormattedMessage id='project.global.fields.bidTimeLeft'/>
            </th>
        </tr>
        </thead>

        <tbody>
        {products.map(product =>
            <tr key={product.id}>
                <td>{selectors.getCategoryName(categories, product.categoriaId)}</td>
                <td>
                    <ProductLink id={product.id} name={product.nombre}/>
                </td>
                <td><FormattedNumber value={product.precioActual} style="currency" currency="EUR"/></td>
                <td>{product.minutosRestantes} <FormattedMessage id='project.global.fields.minutes'/></td>
            </tr>
        )}
        </tbody>

    </table>
);

export default Products;