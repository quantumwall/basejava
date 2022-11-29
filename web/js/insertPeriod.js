const periodTempl = `<div class="row">
    <div class="col-1"></div>
    <div class="col">
        <div class="mb-3">
            <div class="row">
                <div class="col-sm-2">
                    <input class="form-control" type="text" name="\${type}entryDate" placeholder="Начало" onfocus="(this.type = 'date')" onblur="(this.type = 'text')">
                </div>
                <div class="col-sm-2">
                    <input class="form-control" type="text" name="\${type}exitDate" placeholder="Окончание" onfocus="(this.type = 'date')" onblur="(this.type = 'text')">
                </div>
                <div class="col">
                    <input type="checkbox" id="present">
                    <label for="present">настоящее время</label>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <input class="form-control" type="text" name="\${type}title" placeholder="Заголовок">
        </div>
        <div class="mb-3">
            <input class="form-control" type="text" name="\${type}description" placeholder="Описание">
        </div> 
    </div>
</div>
<hr>`;
const companyTempl = `<div class="form-group">
<div class="mb-3">
    <input class="form-control" type="text" name="\${type}" placeholder="Название организации">
</div>
<div class="mb-3">
    <input class="form-control" type="url" name="\${type}url" placeholder="Сайт организации">
</div>`;

function addPeriod(el, type) {
    const parent = document.getElementById(el.parentNode.id);
    parent.insertAdjacentHTML("beforebegin", periodTempl.replaceAll("${type}", type.name));
}

function addCompany(el, type) {
    const parent = document.getElementById(el.parentNode.id);
    parent.insertAdjacentHTML("beforebegin", (companyTempl.replaceAll("${type}", type.name)) + (periodTempl.replaceAll("${type}", type.name)));
}

