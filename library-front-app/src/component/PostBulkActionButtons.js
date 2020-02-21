import React, {Fragment} from "react";
import DeleteButtonWithConfirmation from "./DeleteButtonWithConfirmation";


const PostBulkActionButtons = props => (
    <Fragment>
        <DeleteButtonWithConfirmation {...props} />
    </Fragment>
);
export default PostBulkActionButtons;