from captcha.image import ImageCaptcha
import sys

number = sys.argv[1]
image = ImageCaptcha()
data = image.generate(number)
image.write(number, "captcha.png")
