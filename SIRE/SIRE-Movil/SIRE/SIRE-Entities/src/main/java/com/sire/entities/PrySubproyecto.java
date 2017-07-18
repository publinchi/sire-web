/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author publio
 */
@Getter
@Setter
public class PrySubproyecto {

    Integer codEmpresa, codProyecto, codSubproyecto, codLineaNegocio, codSupervisor;
    String descSubproyecto, nombreUsuario, estado;
    Date fechaEstado;
}
