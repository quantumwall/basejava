const periodTempl = `<div class="row">
    <div class="col-1"></div>
    <div class="col">
        <div class="mb-3">
            <div class="row">
                <div class="col-sm-2">
                    <input class="form-control" type="text" name="\${type}\${count}entryDate" placeholder="Начало" onfocus="(this.type = 'month')" onblur="(this.type = 'text')">
                </div>
                <div class="col-sm-2">
                    <input class="form-control" type="text" name="\${type}\${count}exitDate" placeholder="Окончание" onfocus="(this.type = 'month')" onblur="(this.type = 'text')">
                </div>
            </div>
        </div>
        <div class="mb-3">
            <input class="form-control" type="text" name="\${type}\${count}title" placeholder="Заголовок">
        </div>
        <div class="mb-3">
            <input class="form-control" type="text" name="\${type}\${count}description" placeholder="Описание">
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

let experienceCount = document.getElementsByName("EXPERIENCE").length;
let educationCount = document.getElementsByName("EDUCATION").length;
experienceCount = experienceCount > 0 ? experienceCount - 1 : -1;
educationCount = educationCount > 0 ? educationCount - 1 : -1;

if (document.getElementById("EXPERIENCE") == null) {
    disableButton("EXPERIENCEaddPeriod");
}

if (document.getElementById("EDUCATION") == null) {
    disableButton("EDUCATIONaddPeriod");
}

function disableButton(id) {
    document.getElementById(id).disabled = true;
}

function enableButton(id) {
    document.getElementById(id).disabled = false;
}

function addPeriod(el, type) {
    const parent = document.getElementById(el.parentNode.id);
    count = "experience" === type.toLowerCase() ? experienceCount : educationCount;
    console.log(count);
    parent.insertAdjacentHTML("beforebegin", periodTempl.replaceAll("${type}", type).replaceAll("${count}", count));
}

function addCompany(el, type) {
    const parent = document.getElementById(el.parentNode.id);
    count = "experience" === type.toLowerCase() ? ++experienceCount: ++educationCount;
    console.log(count);
    parent.insertAdjacentHTML("beforebegin", (companyTempl.replaceAll("${type}", type)) + (periodTempl.replaceAll("${type}", type).replaceAll("${count}", count)));
    enableButton(`${type}addPeriod`);
}

