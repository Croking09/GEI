import {Errors} from "../../common/index.js";
import {useState} from "react";
import {FormattedMessage} from "react-intl";
import backend from "../../../backend/index.js";
import {useNavigate} from "react-router-dom";
import CategorySelector from "../../common/components/CategorySelector.jsx";
import {useDispatch} from "react-redux";
import * as actions from '../actions.js';

const InsertProductForm = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [nombre, setNombre] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [duracionPuja, setDuracionPuja] = useState('');
    const [precioSalida, setPrecioSalida] = useState('');
    const [informacionEnvio, setInformacionEnvio] = useState('');
    const [categoriaId, setCategoriaId] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = async event => {

        event.preventDefault();

        if (form.checkValidity()) {

            const response = await backend.productsService.insertProduct(
                nombre, descripcion, duracionPuja, precioSalida, informacionEnvio, categoriaId
            );

            if (response.ok) {
                dispatch(actions.insertionCompleted(response.payload));
                navigate('/products/insertion-completed');
            } else {
                setBackendErrors(response.payload);
            }

        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }

    }

    return (
        <div>
            <Errors errors={backendErrors}
                    onClose={() => setBackendErrors(null)}/>
            <div className="card bg-light border-dark">
                <h5 className="card-header">
                    <FormattedMessage id="project.product.header.insertProduct"/>
                </h5>
                <div className="card-body">
                    <form ref={node => form = node}
                          className="needs-validation" noValidate
                          onSubmit={(e) => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="name" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.name"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="name" className="form-control"
                                       value={nombre}
                                       onChange={e => setNombre(e.target.value)}
                                       autoFocus
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="description" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.description"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="description" className="form-control"
                                       value={descripcion}
                                       onChange={e => setDescripcion(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="duration" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.duration"/>
                            </label>
                            <div className="col-md-4">
                                <input type="number" id="duration" className="form-control"
                                       value={duracionPuja}
                                       onChange={e => setDuracionPuja(e.target.value)}
                                       min='0'
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="outPrice" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.outPrice"/>
                            </label>
                            <div className="col-md-4">
                                <input type="number" step='0.01' id="outPrice" className="form-control"
                                       value={precioSalida}
                                       onChange={e => setPrecioSalida(e.target.value)}
                                       min='0'
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="informacionEnvio" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.informacionEnvio"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="informacionEnvio" className="form-control"
                                       value={informacionEnvio}
                                       onChange={e => setInformacionEnvio(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="category" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.category"/>
                            </label>
                            <div className="col-md-4">
                                <CategorySelector id="category" className="form-control"
                                       value={categoriaId}
                                       onChange={e => setCategoriaId(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-1">
                                <button id="submitFormButton" type="submit" className="btn btn-primary">
                                    <FormattedMessage id="project.global.buttons.insertProduct"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
};

export default InsertProductForm;