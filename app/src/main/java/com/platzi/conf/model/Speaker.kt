package com.platzi.conf.model

import java.io.Serializable

//Serializable, pasa un objetos mediante activities

class Speaker: Serializable {
    var name = ""
    var jobtitle = ""
    var workplace = ""
    var biography = ""
    var twitter = ""
    var image = ""
    var category = 0
}