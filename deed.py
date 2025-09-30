import sys
from typing import override

from PySide6.QtCore import Qt, Slot,Signal
from PySide6.QtGui import QPixmap, QColor
from PySide6.QtWidgets import ( QApplication
                              , QWidget
                              , QLabel
                              , QScrollBar
                              , QHBoxLayout
                              , QVBoxLayout)

from __feature__ import snake_case, true_property



class ColorPicker(QWidget):

    colorChanged = Signal(QColor)

    def __init__(self, parent = None):
        super().__init__(parent)

        fixed_width = 35
        self.__red_sb = QScrollBar()
        self.__red_color = QLabel()
        self.__green_sb = QScrollBar()
        self.__green_color = QLabel()
        self.__blue_sb = QScrollBar()
        self.__blue_color = QLabel()
        self.__mixed_color = QLabel()

        self.__mixed_color.set_fixed_width(fixed_width)

        red_layout = self.__create_channel(self.__red_sb, self.__red_color, 'Red', fixed_width)
        green_layout = self.__create_channel(self.__green_sb, self.__green_color, 'Green', fixed_width)
        blue_layout = self.__create_channel(self.__blue_sb, self.__blue_color, 'Blue', fixed_width)
        
        channel_layout = QVBoxLayout()
        channel_layout.add_layout(red_layout)
        channel_layout.add_layout(green_layout)
        channel_layout.add_layout(blue_layout)

        layout = QHBoxLayout()
        layout.add_layout(channel_layout)
        layout.add_widget(self.__mixed_color)

        self.set_layout(layout)

    def __create_channel(self, sb, color, title_text, fixed_width):
        # Création des widgets requis
        title = QLabel()
        value = QLabel()

        # Configuration des widgets (mutateurs)
        title.text = title_text
        title.set_fixed_width(fixed_width)

        sb.orientation = Qt.Horizontal
        sb.set_range(0, 255)
        sb.value = 0
        sb.minimum_width = 2 * fixed_width

        value.set_num(0)
        value.alignment = Qt.AlignCenter
        value.set_fixed_width(fixed_width)
        
        color.set_fixed_width(fixed_width)

        # Création du layout et assemblage
        layout = QHBoxLayout()
        layout.add_widget(title)
        layout.add_widget(sb)
        layout.add_widget(value)
        layout.add_widget(color)

        # Établissement des connections
        sb.valueChanged.connect(value.set_num)
        sb.valueChanged.connect(self.__update_colors)

        return layout

    def __update_color(self, color_widget, r, g, b):
        image = QPixmap(color_widget.size)
        image.fill(QColor(r, g, b))
        color_widget.pixmap = image

    @Slot()
    def __update_colors(self):
        r = self.__red_sb.value
        g = self.__green_sb.value
        b = self.__blue_sb.value
        self.__update_color(self.__red_color, r, 0, 0)
        self.__update_color(self.__green_color, 0, g, 0)
        self.__update_color(self.__blue_color, 0, 0, b)
        self.__update_color(self.__mixed_color, r, g, b)

        # Émission explicite du signal colorChanged
        self.colorChanged.emit(self.color)

    @override
    def show_event(self, event):
        super().show_event(event)
        self.__update_colors()

    @property
    def color(self):
        r = self.__red_sb.value
        g = self.__green_sb.value
        b = self.__blue_sb.value        
        return QColor(r, g, b)

    @color.setter
    def color(self, value):
        if value != self.color:
            self.__red_sb.value = value.red()
            self.__green_sb.value = value.green()
            self.__blue_sb.value = value.blue()

    @Slot(QColor)
    def set_color(self, value):
        self.color = value





class DemoColorPickers(QWidget):

    def __init__(self):
        super().__init__()

        self._color_pickers = [ColorPicker() for _ in range(5)]

        self._color_pickers[0].colorChanged.connect(self._color_pickers[2].set_color)
        self._color_pickers[2].colorChanged.connect(self._color_pickers[-1].set_color)

        layout = QVBoxLayout()
        for color_picker in self._color_pickers:
            layout.add_widget(color_picker)

        layout.add_stretch()

        self.set_layout(layout)



def main():
    app = QApplication(sys.argv)

    w = DemoColorPickers()
    w.show()

    sys.exit(app.exec())


if __name__ == '__main__':
    main()