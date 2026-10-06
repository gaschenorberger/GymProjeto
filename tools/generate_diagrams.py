from pathlib import Path
from PIL import Image, ImageDraw, ImageFont

ROOT = Path(__file__).resolve().parents[1]
FONT = Path(r"C:\Windows\Fonts\segoeui.ttf")
FONT_BOLD = Path(r"C:\Windows\Fonts\segoeuib.ttf")
INK = "#1f2933"
GREEN = "#0b6b3a"
LIGHT = "#f7faf8"
ACCENT = "#dcefe3"


def font(size, bold=False):
    return ImageFont.truetype(str(FONT_BOLD if bold else FONT), size)


def centered(draw, xy, text, size=28, bold=False, fill=INK):
    box = draw.multiline_textbbox((0, 0), text, font=font(size, bold), align="center", spacing=5)
    x, y = xy
    draw.multiline_text((x - (box[2] - box[0]) / 2, y - (box[3] - box[1]) / 2), text,
                        font=font(size, bold), fill=fill, align="center", spacing=5)


def arrow(draw, start, end, width=4, fill=INK):
    draw.line([start, end], fill=fill, width=width)
    x1, y1 = start
    x2, y2 = end
    import math
    angle = math.atan2(y2 - y1, x2 - x1)
    length = 16
    for delta in (2.55, -2.55):
        draw.line([end, (x2 + length * math.cos(angle + delta), y2 + length * math.sin(angle + delta))],
                  fill=fill, width=width)


def box(draw, center, size, text, text_size=25, fill=LIGHT, radius=18):
    cx, cy = center
    w, h = size
    rect = (cx - w // 2, cy - h // 2, cx + w // 2, cy + h // 2)
    draw.rounded_rectangle(rect, radius=radius, fill=fill, outline=INK, width=4)
    centered(draw, center, text, text_size)
    return rect


def ellipse(draw, center, size, text, text_size=25):
    cx, cy = center
    w, h = size
    rect = (cx - w // 2, cy - h // 2, cx + w // 2, cy + h // 2)
    draw.ellipse(rect, fill=LIGHT, outline=INK, width=4)
    centered(draw, center, text, text_size)
    return rect


def diamond(draw, center, size, text, text_size=23):
    cx, cy = center
    w, h = size
    pts = [(cx, cy - h // 2), (cx + w // 2, cy), (cx, cy + h // 2), (cx - w // 2, cy)]
    draw.polygon(pts, fill=ACCENT, outline=INK)
    draw.line(pts + [pts[0]], fill=INK, width=4)
    centered(draw, center, text, text_size)
    return pts


def base(title, size=(1600, 1100)):
    image = Image.new("RGB", size, "white")
    draw = ImageDraw.Draw(image)
    centered(draw, (size[0] // 2, 52), title, 40, True, GREEN)
    draw.line((100, 92, size[0] - 100, 92), fill=GREEN, width=5)
    return image, draw


def save(image, path):
    path.parent.mkdir(parents=True, exist_ok=True)
    image.save(path, "PNG", optimize=True)


def use_case():
    image, draw = base("Diagrama de Caso de Uso - GymProjeto")
    boundary = (280, 125, 1510, 1010)
    draw.rounded_rectangle(boundary, radius=15, outline=INK, width=4)
    centered(draw, (895, 155), "Sistema de Gerenciamento de Academia", 29, True)

    # Ator
    draw.ellipse((80, 430, 140, 490), outline=INK, width=4)
    draw.line((110, 490, 110, 620), fill=INK, width=4)
    draw.line((45, 535, 175, 535), fill=INK, width=4)
    draw.line((110, 620, 55, 700), fill=INK, width=4)
    draw.line((110, 620, 165, 700), fill=INK, width=4)
    centered(draw, (110, 740), "Usuário", 28, True)

    cases = {
        "login": ((560, 275), "Realizar login"),
        "menu": ((1110, 275), "Acessar menu principal"),
        "alunos": ((650, 460), "Gerenciar alunos\nSalvar • Editar • Excluir • Limpar\nJTable e seleção por clique"),
        "planos": ((650, 650), "Gerenciar planos\nSalvar • Editar • Excluir • Limpar\nJTable e seleção por clique"),
        "matriculas": ((650, 840), "Gerenciar matrículas\nSalvar • Editar • Excluir • Limpar\nJTable e seleção por clique"),
        "sair": ((1110, 900), "Sair do sistema"),
    }
    rects = {}
    for key, (center, text) in cases.items():
        rects[key] = ellipse(draw, center, (430 if key in ("alunos", "planos", "matriculas") else 380, 145), text, 20)

    for key in ("login", "alunos", "planos", "matriculas", "sair"):
        left = rects[key][0]
        cy = (rects[key][1] + rects[key][3]) // 2
        draw.line((175, 535, 275, cy, left, cy), fill=INK, width=3)
    arrow(draw, (750, 275), (920, 275), 3)
    centered(draw, (835, 250), "acesso autorizado", 17)
    save(image, ROOT / "docs/diagrama-caso-uso/Diagrama de caso de uso.png")


def class_box(draw, rect, name, attributes, methods):
    x1, y1, x2, y2 = rect
    draw.rounded_rectangle(rect, radius=12, fill=LIGHT, outline=INK, width=4)
    draw.rectangle((x1, y1, x2, y1 + 58), fill=ACCENT, outline=INK, width=3)
    centered(draw, ((x1 + x2) // 2, y1 + 29), name, 27, True)
    y = y1 + 78
    for value in attributes:
        draw.text((x1 + 18, y), value, font=font(20), fill=INK)
        y += 31
    if methods:
        draw.line((x1, y + 5, x2, y + 5), fill=INK, width=3)
        y += 18
        for value in methods:
            draw.text((x1 + 18, y), value, font=font(19), fill=INK)
            y += 29


def classes():
    image, draw = base("Diagrama de Classes - GymProjeto", (1800, 1150))
    aluno = (60, 180, 490, 700)
    matricula = (660, 250, 1140, 700)
    plano = (1310, 180, 1740, 700)
    usuario = (660, 780, 1140, 1050)
    class_box(draw, aluno, "Aluno", [
        "- id: int", "- nome: String", "- cpf: String", "- email: String",
        "- telefone: String", "- dataNascimento: LocalDate", "- endereço completo: String",
        "- plano: String", "- status: String"], ["+ isAtivo(): boolean", "+ toString(): String"])
    class_box(draw, matricula, "Matricula", [
        "- id: int", "- alunoId: int", "- planoId: int", "- dataInicio: LocalDate",
        "- dataVencimento: LocalDate", "- valor: double", "- situacao: String"], [])
    class_box(draw, plano, "Plano", [
        "- id: int", "- nome: String", "- duracaoMeses: int", "- valor: double",
        "- situacao: String", "- descricao: String"], ["+ toString(): String"])
    class_box(draw, usuario, "Usuario", [
        "- id: int", "- nome: String", "- email: String", "- senhaHash: String", "- perfil: String"], [])
    draw.line((490, 440, 660, 440), fill=INK, width=4)
    draw.text((505, 400), "1", font=font(23, True), fill=INK)
    draw.text((595, 400), "0..*", font=font(23, True), fill=INK)
    draw.line((1140, 440, 1310, 440), fill=INK, width=4)
    draw.text((1155, 400), "0..*", font=font(23, True), fill=INK)
    draw.text((1270, 400), "1", font=font(23, True), fill=INK)
    centered(draw, (900, 740), "Persistência: AlunoDAO • PlanoDAO • MatriculaDAO • UsuarioDAO", 24, True, GREEN)
    save(image, ROOT / "docs/diagrama-classes/diagrama-classes.png")


def activity(title, filename, action, validations, success):
    image, draw = base(title, (1300, 1550))
    cx = 575
    draw.ellipse((cx - 25, 115, cx + 25, 165), fill=INK)
    arrow(draw, (cx, 165), (cx, 220))
    box(draw, (cx, 275), (460, 90), "Realizar login")
    arrow(draw, (cx, 320), (cx, 380))
    diamond(draw, (cx, 450), (330, 140), "Login válido?")
    draw.text((cx + 175, 420), "Não", font=font(22, True), fill=INK)
    arrow(draw, (cx + 165, 450), (1040, 450))
    box(draw, (1080, 450), (300, 90), "Exibir mensagem\nde erro", 22)
    arrow(draw, (cx, 520), (cx, 580))
    draw.text((cx + 18, 535), "Sim", font=font(22, True), fill=INK)
    box(draw, (cx, 635), (520, 90), action, 23)
    arrow(draw, (cx, 680), (cx, 740))
    box(draw, (cx, 795), (560, 90), validations, 21)
    arrow(draw, (cx, 840), (cx, 900))
    diamond(draw, (cx, 970), (350, 140), "Dados válidos?")
    draw.text((cx + 185, 940), "Não", font=font(22, True), fill=INK)
    arrow(draw, (cx + 175, 970), (1040, 970))
    box(draw, (1080, 970), (300, 90), "Informar erro\ne manter campos", 21)
    arrow(draw, (cx, 1040), (cx, 1100))
    draw.text((cx + 18, 1055), "Sim", font=font(22, True), fill=INK)
    box(draw, (cx, 1155), (470, 90), success, 23, ACCENT)
    arrow(draw, (cx, 1200), (cx, 1260))
    box(draw, (cx, 1315), (520, 90), "Atualizar JTable e limpar campos", 22)
    arrow(draw, (cx, 1360), (cx, 1420))
    draw.ellipse((cx - 30, 1420, cx + 30, 1480), outline=INK, width=4)
    draw.ellipse((cx - 20, 1430, cx + 20, 1470), fill=INK)
    save(image, ROOT / "docs/diagrama-atividades" / filename)


def login_activity():
    image, draw = base("Diagrama de Atividades - Realizar Login", (1300, 1550))
    cx = 560
    draw.ellipse((cx - 25, 115, cx + 25, 165), fill=INK)
    arrow(draw, (cx, 165), (cx, 220))
    box(draw, (cx, 275), (480, 90), "Acessar tela de login")
    arrow(draw, (cx, 320), (cx, 380))
    box(draw, (cx, 435), (520, 90), "Informar e-mail e senha")
    arrow(draw, (cx, 480), (cx, 540))
    box(draw, (cx, 595), (460, 90), "Validar campos")
    arrow(draw, (cx, 640), (cx, 700))
    diamond(draw, (cx, 770), (350, 140), "Campos preenchidos?")
    draw.text((cx + 185, 740), "Não", font=font(22, True), fill=INK)
    arrow(draw, (cx + 175, 770), (1030, 770))
    box(draw, (1070, 770), (300, 90), "Informar campos\nobrigatórios", 21)
    draw.line((1220, 770, 1250, 770, 1250, 435, 820, 435), fill=INK, width=3)
    arrow(draw, (cx, 840), (cx, 900))
    draw.text((cx + 18, 855), "Sim", font=font(22, True), fill=INK)
    box(draw, (cx, 955), (500, 90), "Validar credenciais e hash")
    arrow(draw, (cx, 1000), (cx, 1060))
    diamond(draw, (cx, 1130), (350, 140), "Login válido?")
    draw.text((cx + 185, 1100), "Não", font=font(22, True), fill=INK)
    arrow(draw, (cx + 175, 1130), (1030, 1130))
    box(draw, (1070, 1130), (300, 90), "Exibir mensagem\nde erro", 21)
    draw.line((1220, 1130, 1250, 1130, 1250, 435, 820, 435), fill=INK, width=3)
    arrow(draw, (cx, 1200), (cx, 1260))
    draw.text((cx + 18, 1215), "Sim", font=font(22, True), fill=INK)
    box(draw, (cx, 1315), (500, 90), "Acessar menu principal", 23, ACCENT)
    arrow(draw, (cx, 1360), (cx, 1420))
    draw.ellipse((cx - 30, 1420, cx + 30, 1480), outline=INK, width=4)
    draw.ellipse((cx - 20, 1430, cx + 20, 1470), fill=INK)
    save(image, ROOT / "docs/diagrama-atividades/Realizar Login.png")


def main():
    use_case()
    classes()
    login_activity()
    activity("Diagrama de Atividades - Cadastrar Aluno", "Cadastrar Aluno.png",
             "Informar dados do aluno", "Validar obrigatórios, CPF, e-mail e data", "Salvar aluno")
    activity("Diagrama de Atividades - Cadastrar Plano", "Cadastrar Plano.png",
             "Informar dados do plano", "Validar nome, duração e valor", "Salvar plano")
    activity("Diagrama de Atividades - Cadastrar Matrícula", "Cadastrar Matricula.png",
             "Selecionar aluno e plano e informar datas", "Validar vínculos ativos, datas e valor", "Salvar matrícula")


if __name__ == "__main__":
    main()
