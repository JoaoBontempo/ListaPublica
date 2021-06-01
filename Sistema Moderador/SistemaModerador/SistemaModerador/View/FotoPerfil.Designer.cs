
namespace SistemaModerador.View
{
    partial class FotoPerfil
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.pbxFotoPerfil = new System.Windows.Forms.PictureBox();
            this.btnExcluirEndereco = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.pbxFotoPerfil)).BeginInit();
            this.SuspendLayout();
            // 
            // pbxFotoPerfil
            // 
            this.pbxFotoPerfil.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.pbxFotoPerfil.Location = new System.Drawing.Point(13, 13);
            this.pbxFotoPerfil.Name = "pbxFotoPerfil";
            this.pbxFotoPerfil.Size = new System.Drawing.Size(417, 379);
            this.pbxFotoPerfil.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pbxFotoPerfil.TabIndex = 0;
            this.pbxFotoPerfil.TabStop = false;
            // 
            // btnExcluirEndereco
            // 
            this.btnExcluirEndereco.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.btnExcluirEndereco.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(8)))), ((int)(((byte)(15)))), ((int)(((byte)(0)))));
            this.btnExcluirEndereco.Cursor = System.Windows.Forms.Cursors.Hand;
            this.btnExcluirEndereco.FlatAppearance.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.btnExcluirEndereco.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnExcluirEndereco.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.btnExcluirEndereco.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.btnExcluirEndereco.Location = new System.Drawing.Point(12, 398);
            this.btnExcluirEndereco.Name = "btnExcluirEndereco";
            this.btnExcluirEndereco.Size = new System.Drawing.Size(418, 30);
            this.btnExcluirEndereco.TabIndex = 45;
            this.btnExcluirEndereco.Text = "Excluir foto";
            this.btnExcluirEndereco.UseVisualStyleBackColor = false;
            this.btnExcluirEndereco.Click += new System.EventHandler(this.btnExcluirEndereco_Click);
            // 
            // FotoPerfil
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.ClientSize = new System.Drawing.Size(442, 443);
            this.Controls.Add(this.btnExcluirEndereco);
            this.Controls.Add(this.pbxFotoPerfil);
            this.Name = "FotoPerfil";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "FotoPerfil";
            ((System.ComponentModel.ISupportInitialize)(this.pbxFotoPerfil)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox pbxFotoPerfil;
        private System.Windows.Forms.Button btnExcluirEndereco;
    }
}