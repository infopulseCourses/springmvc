export class Link {

    public rel: string = "";

    public href: string = "";
}

export class UserLink {

    public login: string = "";

    public links: Link[] = undefined;
}


export class User {

    public users: UserLink[] = undefined;
}