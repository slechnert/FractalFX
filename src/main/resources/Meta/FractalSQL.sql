Create Table IF NOT EXISTS user (
    user_name varchar(24),
    password  varchar(32),
    email     varchar(32),
    PRIMARY KEY (user_name)
);

Create Table IF NOT EXISTS CustomRGB(
    customRGB_ID int(8) NOT NULL AUTO_INCREMENT,
    r_factor double(4,3),
    g_factor double(4,3),
    b_factor double(4,3),
    PRIMARY KEY (customRGB_ID)
);

Create Table IF NOT EXISTS Color(
    color_ID int(8) NOT NULL AUTO_INCREMENT,
    red int(3),
    green int(3),
    blue int(3),
    PRIMARY KEY (color_ID)
);

Create Table IF NOT EXISTS Fractal(
    fractal_ID   int(8) NOT NULL AUTO_INCREMENT,
    customRGB_ID int(8),
    base_color   int(8),
    color_scheme varchar(7),
    convergence_steps int(4),
    re_min double(11,10),
    re_max double(11,10),
    im_min double(11,10),
    im_max double(11,10),
    z double(5, 3),
    zi double(5, 3),
    PRIMARY KEY (fractal_ID),
    FOREIGN KEY (customRGB_ID) REFERENCES CustomRGB (customRGB_ID),
    FOREIGN KEY (base_color) REFERENCES color (color_ID)
    );

Create Table IF NOT EXISTS CustomSet(
    user_name  varchar(24),
    fractal_ID int(8),
    set_name   varchar(24),
    PRIMARY KEY (user_name, fractal_ID),
    FOREIGN KEY (fractal_ID) REFERENCES  Fractal (fractal_ID),
    FOREIGN KEY (user_name) REFERENCES User(user_name)
);

ALTER TABLE customRGB AUTO_INCREMENT=100;
ALTER TABLE color AUTO_INCREMENT=1000;
