import {useSelector} from 'react-redux';
import {Link} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

import users from '../../users';
import {FindProducts} from "../../catalog/index.js";
import Button from "bootstrap/js/src/button.js";

const Header = () => {

    const userName = useSelector(users.selectors.getUserName);

    return (

        <nav className="navbar navbar-expand-lg navbar-light bg-light border">
            <Link className="navbar-brand" to="/">PA Project</Link>
            <button className="navbar-toggler" type="button" 
                data-toggle="collapse" data-target="#navbarSupportedContent" 
                aria-controls="navbarSupportedContent" aria-expanded="false" 
                aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">

                <ul className="navbar-nav mr-auto">
                    <li>
                        <FindProducts/>
                    </li>
                </ul>
                
                {userName ?

                    <ul className="navbar-nav">

                        <li className="nav-item">
                            <Link id="insertProductLink" className="nav-link" to="/products/add">
                                <FormattedMessage id="project.product.header.insertProduct"/>
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link id="myBidsLink" className="nav-link" to="/bid/find-bids">
                                <FormattedMessage id="project.app.Header.MyBids" defaultMessage="Mis Pujas"/>
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link id="myProductsLink" className="nav-link" to="/products/find-products">
                                <FormattedMessage id="project.app.Header.MyProducts" defaultMessage="Mis Productos"/>
                            </Link>
                        </li>

                        <li id="userForm" className="nav-item dropdown">

                            <a id="userNameLogin" className="dropdown-toggle nav-link" href="/"
                               data-toggle="dropdown">
                                <span className="fa-solid fa-user"></span>&nbsp;
                                {userName}
                            </a>
                            <div className="dropdown-menu dropdown-menu-right">
                                <Link className="dropdown-item" to="/users/update-profile">
                                    <FormattedMessage id="project.users.UpdateProfile.title"/>
                                </Link>
                                <Link className="dropdown-item" to="/users/change-password">
                                    <FormattedMessage id="project.users.ChangePassword.title"/>
                                </Link>
                                <div className="dropdown-divider"></div>
                                <Link className="dropdown-item" to="/users/logout">
                                    <FormattedMessage id="project.app.Header.logout"/>
                                </Link>
                            </div>

                        </li>

                    </ul>

                    :

                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link id="loginLink" className="nav-link" to="/users/login">
                                <FormattedMessage id="project.users.Login.title"/>
                            </Link>
                        </li>
                    </ul>
                
                }

            </div>
        </nav>

    );

};

export default Header;
