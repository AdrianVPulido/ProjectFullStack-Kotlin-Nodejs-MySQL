module.exports = app => {

    const Mesas = app.db.models.mesas;

    app.route('/pagina/mesas')
    .get((req, res) => {
        Mesas.findAll({})
        .then(result => res.render('mesas',{
            data: result
         }))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
    .post((req, res) => {
        Mesas.create(req.body)
        .then(result => res.render('mesas',{
            data: result
         }))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
        res.redirect('/pagina/mesas');
    });
    app.route('/pagina/mesas/:idmesa')
    .get((req, res) => {
        Mesas.findOne({where: req.params})
        .then(result => res.render('mesas_edit',{
            data: result
         }))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
    .put((req, res) => {
        Mesas.update(req.body,{where: req.params})
        .then(result => res.render('mesas_edit',{
            data: req.body
         }))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
        res.redirect('/pagina/mesas')
    });

    app.route('/pagina/mesa/delete/:idmesa')
        .delete((req, res) => {
        Mesas.destroy({where: req.params})
        .then(result => res.sendStatus(204))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
};