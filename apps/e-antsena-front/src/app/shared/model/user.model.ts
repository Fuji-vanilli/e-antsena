export interface BaseUser {
    firstname?: string;
    lastname?: string;
    email?: string;
    publicID?: string,
    imageUrl?: string
}

export interface ConnectedUser extends BaseUser {
    authorities?: string[];  
}